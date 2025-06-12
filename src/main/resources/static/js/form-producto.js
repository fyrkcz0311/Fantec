function mostrarPreview(input) {
    const preview = document.getElementById('preview');
    preview.innerHTML = ''; // limpiar previews anteriores

    Array.from(input.files).forEach(file => {
        const reader = new FileReader();
        reader.onload = e => {
            const img = document.createElement('img');
            img.src = e.target.result;
            img.style.height = '100px';
            img.className = 'img-thumbnail';
            preview.appendChild(img);
        };
        reader.readAsDataURL(file);
    });
}
