// fetches comments from the server and populates the html element with id 'comments'
async function getComments() {
    const response = await fetch('/comments');
    const json = await response.text(); // collection of comments
    document.getElementById('comments').innerText = json;
}
