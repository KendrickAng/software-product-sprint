// fetches comments from the server and populates the html element with id 'comments'
async function getComments() {
    const response = await fetch('/comments');
    const comments = await response.json(); // collection of comments
    const container = document.getElementById('comments');
    insertComments(comments, container);
}

// Accepts an array of { content: String, timestamp: long }, and adds them to the 'comments' element
function insertComments(comments, container) {
    console.log(comments);
    const commentsContainer = document.createElement('ul');
    commentsContainer.className = 'comments__container';

    for (let { content, timestamp } of comments) {
        const commentsItem = document.createElement('li');
        const date = new Date(timestamp).toDateString();
        commentsItem.innerText = `${content} ${date}`;
        commentsContainer.appendChild(commentsItem);
    }

    container.appendChild(commentsContainer);
}
