/*
TODO https://stackoverflow.com/questions/44502243/single-navigation-bar-across-website
Extract nav render code into this script, then add the navbar to the document by running this script.
 */
async function addLoginToNav() {
    const res = await fetch("/auth");
    const navLogin = document.getElementById("nav__login");
    if (res.ok) {
        navLogin.innerHTML = await res.text();
    } else {
        navLogin.setAttribute('display', 'none');
        // TODO: Possibly extract out fetch handling login to utils.js
        console.error(`${res.status} ${res.statusText}`);
    }
}

addLoginToNav().catch(err => console.error(err));
