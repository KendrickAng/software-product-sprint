/*
Stores all the global scripts to be run on page load for ALL html files.
 */
const CLASSNAME_NAV = 'nav';
const CLASSNAME_LOGO = 'nav__logo';
const CLASSNAME_NAV_ITEMS = 'nav__items';
const CLASSNAME_NAV_ITEM = 'nav__item';
const LINK_ROOT = "/index.html";
const LINK_PROJECTS = "/navigation/projects/projects.html";
const LINK_ORIGAMI = "/navigation/origami/origami.html";
const LINK_FEEDBACK = "/navigation/feedback/feedback.html";
const HEADER_ROOT = "HOME";
const HEADER_PROJECTS = "Projects";
const HEADER_ORIGAMI = "Origami";
const HEADER_FEEDBACK = "Feedback";

async function loadNav() {
    const nav = document.createElement('nav');
    nav.className = CLASSNAME_NAV;

    const navLogo = document.createElement('div');
    navLogo.className = CLASSNAME_LOGO;
    const aLogo = document.createElement('a');
    aLogo.href = LINK_ROOT;
    aLogo.innerText = HEADER_ROOT;
    navLogo.appendChild(aLogo);

    const navItems = document.createElement('div');
    navItems.className = CLASSNAME_NAV_ITEMS;

    // Projects page
    const navItemProjects = document.createElement('div');
    navItemProjects.className = CLASSNAME_NAV_ITEM;
    const aProjects = document.createElement('a');
    aProjects.href = LINK_PROJECTS;
    aProjects.innerText = HEADER_PROJECTS;
    navItemProjects.appendChild(aProjects);

    // Origami page
    const navItemOrigami = document.createElement('div');
    navItemOrigami.className = CLASSNAME_NAV_ITEM;
    const aOrigami = document.createElement('a');
    aOrigami.href = LINK_ORIGAMI;
    aOrigami.innerText = HEADER_ORIGAMI;
    navItemOrigami.appendChild(aOrigami);

    // Feedback page. Login only
    const navItemFeedback = document.createElement('div');
    navItemFeedback.className = CLASSNAME_NAV_ITEM;
    const aFeedback = document.createElement('a');
    aFeedback.href = LINK_FEEDBACK;
    aFeedback.innerText = HEADER_FEEDBACK;
    navItemFeedback.append(aFeedback);

    // Login/Logout link
    const navItemLogin = document.createElement('div');
    navItemLogin.className = CLASSNAME_NAV_ITEM;
    fetch("/auth/generate-login-link")
        .then(res => res.text())
        .then(authLink => navItemLogin.innerHTML = authLink)
        .catch(err => console.error(err));

    // Build the nav
    navItems.appendChild(navItemProjects);
    navItems.appendChild(navItemOrigami);
    fetch("/auth")
        .then(res => res.text())
        .then(isAuthRes => {
            if (isAuthRes === String(true)) {
                // auth-specific pages
                insertBefore(navItemLogin, navItemFeedback);
            }
        });
    navItems.appendChild(navItemLogin);
    nav.appendChild(navLogo);
    nav.appendChild(navItems);

    const body = document.getElementsByTagName('body')[0];
    body.insertBefore(nav, body.firstChild);
}

loadNav().catch(err => console.error(err));

/*
============================== UTILITY FUNCTIONS ==============================
 */
function insertBefore(referenceNode, newNode) {
    referenceNode.parentNode.insertBefore(newNode, referenceNode);
}
function insertAfter(referenceNode, newNode) {
    referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
}
