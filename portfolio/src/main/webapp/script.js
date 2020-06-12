async function getComments() {
    const response = await fetch('/comments');
    const json = await response.text(); // collection of comments
    console.log(typeof json);

    document.getElementById('comments').innerText = json;
}
