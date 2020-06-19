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
const LINK_GITHUB = "https://github.com/KendrickAng";
const LINK_LINKEDIN = "https://www.linkedin.com/in/kendrickang/";
const HEADER_ROOT = "HOME";
const HEADER_PROJECTS = "Projects";
const HEADER_ORIGAMI = "Origami";
const HEADER_FEEDBACK = "Feedback";
const HEADER_GITHUB = "Github";
const HEADER_LINKEDIN = "LinkedIn";

async function loadNav() {
    const isAuth = await fetch("/auth");

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

    // Github hyperlink
    const navItemGithub = document.createElement('div');
    navItemGithub.className = CLASSNAME_NAV_ITEM;
    const aGithub = document.createElement('a');
    aGithub.href = LINK_GITHUB;
    aGithub.innerText = HEADER_GITHUB;
    navItemGithub.appendChild(aGithub);

    // Linkedin hyperlink
    const navItemLinkedIn = document.createElement('div');
    navItemLinkedIn.className = CLASSNAME_NAV_ITEM;
    const aLinkedIn = document.createElement('a');
    aLinkedIn.href = LINK_LINKEDIN;
    aLinkedIn.innerText = HEADER_LINKEDIN;
    navItemLinkedIn.appendChild(aLinkedIn);

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
    navItems.appendChild(navItemGithub);
    navItems.appendChild(navItemLinkedIn);
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

function insertBefore(referenceNode, newNode) {
    referenceNode.parentNode.insertBefore(newNode, referenceNode);
}
function insertAfter(referenceNode, newNode) {
    referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
}

loadNav().catch(err => console.error(err));
