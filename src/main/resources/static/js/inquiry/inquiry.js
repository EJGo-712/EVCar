'use strict';

document.addEventListener('DOMContentLoaded', () => {
    const titleInput = document.getElementById('title');

    if (!titleInput) {
        return;
    }

    titleInput.addEventListener('input', () => {
        if (titleInput.value.length > 200) {
            titleInput.value = titleInput.value.substring(0, 200);
        }
    });
});