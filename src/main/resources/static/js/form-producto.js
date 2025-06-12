let archivosSeleccionados = [];

function mostrarPreview(input) {
    const preview = document.getElementById('preview');
    const nuevosArchivos = Array.from(input.files);

    nuevosArchivos.forEach(file => {
        archivosSeleccionados.push(file);

        const reader = new FileReader();
        reader.onload = e => {
            const container = document.createElement('div');
            container.className = 'position-relative me-2';
            container.style.display = 'inline-block';

            const img = document.createElement('img');
            img.src = e.target.result;
            img.className = 'img-thumbnail';
            img.style.height = '100px';

            const btn = document.createElement('button');
            btn.type = 'button';
            btn.innerHTML = '&times;';
            btn.className = 'btn btn-danger btn-sm position-absolute';
            btn.style.top = '-8px';
            btn.style.right = '-8px';
            btn.style.padding = '2px 6px';
            btn.onclick = function () {
                const index = archivosSeleccionados.indexOf(file);
                if (index > -1) archivosSeleccionados.splice(index, 1);
                actualizarInputFiles(input);
                container.remove();
            };

            container.appendChild(img);
            container.appendChild(btn);
            preview.appendChild(container);
        };
        reader.readAsDataURL(file);
    });

    // Limpia el input real para que permita seleccionar el mismo archivo de nuevo si se desea
    input.value = '';
    actualizarInputFiles(input);
}

function actualizarInputFiles(input) {
    const dataTransfer = new DataTransfer();
    archivosSeleccionados.forEach(file => dataTransfer.items.add(file));
    input.files = dataTransfer.files;
}
