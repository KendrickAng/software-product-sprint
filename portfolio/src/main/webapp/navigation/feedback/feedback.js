const CLASSNAME_COMMENTS_CONTAINER = 'comments__container';
const CLASSNAME_COMMENTS_ITEM = 'comments__item';
const ID_COMMENTS = 'comments';
const DEFAULT_USER_NAME = 'Anonymous';

// fetches comments from the server and populates the html element with id 'comments'.
async function getComments() {
    const response = await fetch('/comments');
    const container = document.getElementById(ID_COMMENTS);
    if (response.ok) {
        const comments = await response.json(); // collection of comments
        insertComments(comments, container);
    } else {
        handleError(response, container);
    }
}

// Accepts an array of { content: String, timestamp: long }, and adds them to the 'comments' element.
function insertComments(comments, container) {
    const commentsContainer = document.createElement('ul');
    commentsContainer.className = CLASSNAME_COMMENTS_CONTAINER;

    comments.forEach(comment => {
        const { name=DEFAULT_USER_NAME, content, timestamp} = comment;
        const date = new Date(timestamp).toDateString();

        const commentsItem = document.createElement('li');
        commentsItem.className = CLASSNAME_COMMENTS_ITEM;
        commentsItem.innerText = `${content} - ${name}, ${date}`;

        commentsContainer.appendChild(commentsItem);
    });

    container.appendChild(commentsContainer);
}

function handleError(response, container) {
    console.error(`${response.status} ${response.statusText}`);
    container.innerText = "We can't retrieve the comments now. Please try again later 😞";
}
