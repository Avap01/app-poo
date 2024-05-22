document.addEventListener('DOMContentLoaded', function () {
    const uploadInput = document.getElementById('upload');
    const saveButton = document.getElementById('save-button');
    const editedImage = document.getElementById('edited-image');

    uploadInput.addEventListener('change', function (event) {
        const file = event.target.files[0];
        if (file) {
            const formData = new FormData();
            formData.append('file', file);

            fetch('http://localhost:8080/api/images/upload', { 
                body: formData
            })
            .then(response => response.blob())
            .then(blob => {
                const url = URL.createObjectURL(blob);
                editedImage.src = url;
                editedImage.style.display = 'block';
                saveButton.style.display = 'block';
            })
            .catch(error => {
                console.error('Erro ao processar a imagem:', error);
            });
        }
    });

    saveButton.addEventListener('click', function () {
        const link = document.createElement('a');
        link.href = editedImage.src;
        link.download = 'edited-image.png';
        link.click();
    });
});
