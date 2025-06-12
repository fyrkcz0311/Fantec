let archivosSeleccionados = [];

function mostrarPreview(input) {
    const preview = document.getElementById('preview');
    preview.innerHTML = '';
    archivosSeleccionados = Array.from(input.files);

    archivosSeleccionados.forEach((file, index) => {
        const reader = new FileReader();
        reader.onload = e => {
            const container = document.createElement('div');
            container.className = 'position-relative me-2';
            container.style.display = 'inline-block';

            const img = document.createElement('img');
            img.src = e.target.result;
            img.style.height = '100px';
            img.className = 'img-thumbnail';

            const btn = document.createElement('button');
            btn.type = 'button';
            btn.innerHTML = '&times;';
            btn.className = 'btn btn-danger btn-sm position-absolute';
            btn.style.top = '-8px';
            btn.style.right = '-8px';
            btn.style.padding = '2px 6px';
            btn.onclick = function () {
                archivosSeleccionados.splice(index, 1);
                actualizarInputFiles(input);
                container.remove();
            };

            container.appendChild(img);
            container.appendChild(btn);
            preview.appendChild(container);
        };
        reader.readAsDataURL(file);
    });
}

function actualizarInputFiles(input) {
    const dataTransfer = new DataTransfer();
    archivosSeleccionados.forEach(file => dataTransfer.items.add(file));
    input.files = dataTransfer.files;
}
