const CLASSNAME_COMMENTS_CONTAINER = 'comments__container';
const CLASSNAME_COMMENTS_ITEM = 'comments__item';
const ID_COMMENTS = 'comments';
const DEFAULT_USER_NAME = 'Anonymous';

// fetches comments from the server and populates the html element with id 'comments'
async function getComments() {
    const response = await fetch('/comments');
    const comments = await response.json(); // collection of comments
    const container = document.getElementById(ID_COMMENTS);
    insertComments(comments, container);
}

// Accepts an array of { content: String, timestamp: long }, and adds them to the 'comments' element
function insertComments(comments, container) {
    console.log(comments);
    const commentsContainer = document.createElement('ul');
    commentsContainer.className = CLASSNAME_COMMENTS_CONTAINER;

    for (let { name, content, timestamp } of comments) {
        name = name ? name : DEFAULT_USER_NAME;
        const commentsItem = document.createElement('li');
        commentsItem.className = CLASSNAME_COMMENTS_ITEM;
        const date = new Date(timestamp).toDateString();
        commentsItem.innerText = `${content} - ${name}, ${date}`;
        commentsContainer.appendChild(commentsItem);
    }

    container.appendChild(commentsContainer);
}
