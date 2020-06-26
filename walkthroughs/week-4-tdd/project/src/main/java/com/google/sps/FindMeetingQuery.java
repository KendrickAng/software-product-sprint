// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class FindMeetingQuery {
  public static final Collection<TimeRange> EMPTY_QUERY = new ArrayList<>();
  public static final Collection<TimeRange> FULL_QUERY = Stream.of(TimeRange.WHOLE_DAY).collect(Collectors.toList());
  /**
   * Assumptions: request and events are all valid, but can be non-null.
   * - Request has duration larger than 0, and at least one attendee.
   * - Each Event has a time range within the day, and may have no attendees.
   */
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    if (events == null || request == null) {
      throw new RuntimeException("input variables should be non-null!");
    }

    // start off with all time slots being free
    Collection<TimeRange> ret = new ArrayList<>();

    long meetingDuration = request.getDuration();
    Collection<String> meetingAttendees = request.getAttendees();
    boolean[] freeTimePoints = new boolean[TimeRange.END_OF_DAY - TimeRange.START_OF_DAY + 1];
    Arrays.fill(freeTimePoints, true);

    // no attendees
    if (meetingAttendees.isEmpty()) {
      return FULL_QUERY;
    }

    // request too long
    if (meetingDuration > TimeRange.WHOLE_DAY.duration()) {
      return EMPTY_QUERY;
    }

    // filter out events where no attendees are involved in the meeting.
    events = events.stream().filter(event -> {
      for (String attendee: event.getAttendees()) {
        if (meetingAttendees.contains(attendee)) {
          return true;
        }
      }
      return false;
    }).collect(Collectors.toList());

    // go through all requests and split the remaining timings.
    events.forEach(event -> {
      int start = event.getWhen().start();
      int end = event.getWhen().end();
      for (int i = start; i < end; i++) {
        freeTimePoints[i] = false;
      }
    });

    return generateResult(freeTimePoints, meetingDuration);
  }

  /**
   * Returns all time ranges that meet the minimum duration requirement.
   * E.g [T T T F T] -> { TimeRange(0, 3, false), TimeRange(4, 5, false) }
   */
  private Collection<TimeRange> generateResult(boolean[] freeTimePoints, long minimumDuration) {
    Collection<TimeRange> ret = new ArrayList<>();
    int marker = 0;
    boolean markerDropped = false;

    for (int current = 0; current < freeTimePoints.length; current++) {
      if (!freeTimePoints[current] && markerDropped) {
        if (current - marker + 1 >= minimumDuration) {
          ret.add(TimeRange.fromStartEnd(marker, current, false));
        }
        markerDropped = false;
      } else if (freeTimePoints[current] && !markerDropped) {
        marker = current;
        markerDropped = true;
      }
      // otherwise, continue
    }

    if (markerDropped) {
      ret.add(TimeRange.fromStartEnd(marker, freeTimePoints.length, false));
    }
    return ret;
  }
}
