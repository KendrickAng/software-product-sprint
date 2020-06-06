// number of images in the origami folder, hardcoded :(
const N = 20;
const PATH_PREFIX = 'images/origami/';
const CARD_CN = 'gallery__card';

function loadOrigamiToGallery() {
    const gallery = document.getElementById('gallery');
    // 1-indexed for easier reference
    const origami = [
        '', 'bird', 'tulip', 'crab', 'flower', 'frog', 'boat', 'swan', 'raccoon', 'fox', 'dog', 'cat',
        'butterfly', 'mouse', 'penguin', 'pinwheel', 'fish', 'turtle', 'rabbit', 'elephant', 'heart'
    ];

    // create cards for every image. A card is defined to have an <img> element and <span> element
    for (let i = 1; i <= N; i++) {
        const card = document.createElement('div');
        card.className = CARD_CN;

        const img = document.createElement('img');
        const src = i < 10
            ? `${PATH_PREFIX}00${i}-${origami[i]}.svg`
            : `${PATH_PREFIX}0${i}-${origami[i]}.svg`;
        img.setAttribute('src', src);
        img.setAttribute('alt', 'Origami');

        const span = document.createElement('span');
        span.innerHTML = origami[i];

        card.appendChild(img);
        card.appendChild(span);
        gallery.appendChild(card);
    }
}

loadOrigamiToGallery();
