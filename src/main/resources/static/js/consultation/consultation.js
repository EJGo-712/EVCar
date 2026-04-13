'use strict';

document.addEventListener('DOMContentLoaded', () => {
    initPreferredDatetimePlaceholder();
    initPaginationDisabledState();
});

function initPreferredDatetimePlaceholder() {
    const preferredDatetimeInput = document.getElementById('preferredDatetime');

    if (!preferredDatetimeInput) {
        return;
    }

    preferredDatetimeInput.addEventListener('focus', () => {
        preferredDatetimeInput.placeholder = '예: 2026-04-11 14:00';
    });
}

function initPaginationDisabledState() {
    const disabledLinks = document.querySelectorAll('.ev-pagination__link.is-disabled');

    disabledLinks.forEach((link) => {
        link.setAttribute('tabindex', '-1');
        link.setAttribute('aria-disabled', 'true');

        link.addEventListener('click', (event) => {
            event.preventDefault();
        });
    });
}