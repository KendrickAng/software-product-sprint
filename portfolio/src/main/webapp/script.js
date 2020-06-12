async function getIntroduction() {
    const response = await fetch('/introduction');
    const text = await response.text();
    document.getElementById('introduction').innerHTML = text;
}
