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

public final class FindMeetingQuery {
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
    ret.add(TimeRange.WHOLE_DAY);

    long meetingDuration = request.getDuration();
    Collection<String> meetingAttendees = request.getAttendees();

    // no attendees
    if (meetingAttendees.isEmpty()) {
      return ret;
    }

    return ret;
  }
}
