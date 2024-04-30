const formEl = document.getElementById('gen-form');

formEl.addEventListener('submit', e => {
    e.preventDefault();
    const formData = new FormData(formEl);
    const data = {};
    formData.forEach((value, key) => data[key] = value);
    fetch('/gen', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    }).catch(err => console.error(err));
});