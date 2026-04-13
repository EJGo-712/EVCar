'use strict';

document.addEventListener('DOMContentLoaded', () => {
    const faqItems = document.querySelectorAll('.ev-faq-item');

    faqItems.forEach((item) => {
        const button = item.querySelector('.ev-faq-item__question');

        if (!button) {
            return;
        }

        button.addEventListener('click', () => {
            item.classList.toggle('is-open');
        });
    });
});