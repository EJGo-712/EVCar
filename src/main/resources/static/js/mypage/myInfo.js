'use strict';

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('myInfoForm');
    const editModeButton = document.getElementById('editModeButton');
    const saveButton = document.getElementById('saveButton');
    const cancelButton = document.getElementById('cancelButton');

    if (!form || !editModeButton || !saveButton || !cancelButton) {
        return;
    }

    const MIN_VEHICLE_YEAR = 1900;

    const editableInputs = Array.from(form.querySelectorAll('[data-editable="true"]'));
    const genderInputs = Array.from(form.querySelectorAll('input[name="gender"]'));
    const hasVehicleInputs = Array.from(form.querySelectorAll('input[name="hasVehicle"]'));
    const ownedVehicleFields = Array.from(form.querySelectorAll('.ev-vehicle-owned-field'));

    const fields = {
        name: form.querySelector('input[name="name"]'),
        birthDate: form.querySelector('input[name="birthDate"]'),
        phone: form.querySelector('input[name="phone"]'),
        address: form.querySelector('input[name="address"]'),
        email: form.querySelector('input[name="email"]'),
        currentPassword: form.querySelector('input[name="currentPassword"]'),
        newPassword: form.querySelector('input[name="newPassword"]'),
        newPasswordConfirm: form.querySelector('input[name="newPasswordConfirm"]'),
        vehicleModel: form.querySelector('input[name="vehicleModel"]'),
        vehicleYear: form.querySelector('input[name="vehicleYear"]'),
        drivingDistance: form.querySelector('input[name="drivingDistance"]')
    };

    const initialValues = new Map();

    const alertAndFocus = (message, element) => {
        window.alert(message);
        if (element) {
            element.focus();
        }
        return false;
    };

    const getInputValue = (input) => input?.value?.trim() ?? '';

    const getHasVehicleValue = () => {
        const checked = form.querySelector('input[name="hasVehicle"]:checked');
        return checked ? checked.value : '';
    };

    const isVehicleOwned = () => {
        const value = getHasVehicleValue();
        return value === 'yes' || value === 'Y' || value === 'y';
    };

    const isEditing = () => !saveButton.classList.contains('ev-myinfo-hidden');

    const saveInitialValues = () => {
        form.querySelectorAll('input').forEach((input) => {
            if (input.type === 'radio') {
                initialValues.set(`${input.name}:${input.value}`, input.checked);
                return;
            }

            initialValues.set(input.name, input.value ?? '');
        });
    };

    const restoreInitialValues = () => {
        form.querySelectorAll('input').forEach((input) => {
            if (input.type === 'radio') {
                input.checked = Boolean(initialValues.get(`${input.name}:${input.value}`));
                return;
            }

            input.value = initialValues.get(input.name) ?? '';
        });

        updateOwnedVehicleFieldsState();
    };

    const clearOwnedVehicleFields = () => {
        ownedVehicleFields.forEach((field) => {
            field.value = '';
        });
    };

    const updateOwnedVehicleFieldsState = () => {
        const vehicleOwned = isVehicleOwned();
        const editing = isEditing();

        ownedVehicleFields.forEach((field) => {
            if (!editing) {
                field.readOnly = true;
                field.disabled = false;
                return;
            }

            if (!vehicleOwned) {
                field.value = '';
                field.readOnly = true;
                field.disabled = true;
                return;
            }

            field.readOnly = false;
            field.disabled = false;
        });
    };

    const toggleActionButtons = (editing) => {
        editModeButton.classList.toggle('ev-myinfo-hidden', editing);
        saveButton.classList.toggle('ev-myinfo-hidden', !editing);
        cancelButton.classList.toggle('ev-myinfo-hidden', !editing);
    };

    const updateEditableFieldsState = (editing) => {
        editableInputs.forEach((input) => {
            if (ownedVehicleFields.includes(input)) {
                return;
            }

            input.readOnly = !editing;
            input.disabled = false;
        });

        genderInputs.forEach((input) => {
            input.disabled = true;
        });

        hasVehicleInputs.forEach((input) => {
            input.disabled = false;
        });

        updateOwnedVehicleFieldsState();
    };

    const setEditMode = (editing) => {
        toggleActionButtons(editing);
        updateEditableFieldsState(editing);
    };

    const validateRequiredField = (input, message) => {
        if (!input || getInputValue(input)) {
            return true;
        }

        return alertAndFocus(message, input);
    };

    const validateBasicFields = () => {
        if (!validateRequiredField(fields.name, '이름을 입력해주세요.')) {
            return false;
        }

        if (!validateRequiredField(fields.birthDate, '생년월일을 입력해주세요.')) {
            return false;
        }

        if (!validateRequiredField(fields.phone, '전화번호를 입력해주세요.')) {
            return false;
        }

        if (!validateRequiredField(fields.address, '주소를 입력해주세요.')) {
            return false;
        }

        if (!validateRequiredField(fields.email, '이메일을 입력해주세요.')) {
            return false;
        }

        return true;
    };

    const validateVehicleYear = () => {
        const vehicleYear = getInputValue(fields.vehicleYear);
        const currentYear = new Date().getFullYear();

        if (!vehicleYear) {
            return alertAndFocus('보유차량 연식을 입력해주세요.', fields.vehicleYear);
        }

        if (!/^\d{4}$/.test(vehicleYear)) {
            return alertAndFocus('보유차량 연식은 4자리 숫자(YYYY)로 입력해주세요.', fields.vehicleYear);
        }

        const parsedYear = Number(vehicleYear);

        if (Number.isNaN(parsedYear)) {
            return alertAndFocus('보유차량 연식은 숫자로 입력해주세요.', fields.vehicleYear);
        }

        if (parsedYear < MIN_VEHICLE_YEAR) {
            return alertAndFocus('보유차량 연식이 올바르지 않습니다.', fields.vehicleYear);
        }

        if (parsedYear > currentYear) {
            return alertAndFocus('보유차량 연식은 현재 연도보다 클 수 없습니다.', fields.vehicleYear);
        }

        return true;
    };

    const validateDrivingDistance = () => {
        const drivingDistance = getInputValue(fields.drivingDistance);

        if (!drivingDistance) {
            return alertAndFocus('주행거리를 입력해주세요.', fields.drivingDistance);
        }

        const parsedDistance = Number(fields.drivingDistance.value);

        if (Number.isNaN(parsedDistance)) {
            return alertAndFocus('주행거리는 숫자로 입력해주세요.', fields.drivingDistance);
        }

        if (parsedDistance < 0) {
            return alertAndFocus('주행거리는 0 이상이어야 합니다.', fields.drivingDistance);
        }

        return true;
    };

    const validateOwnedVehicleFields = () => {
        if (!isVehicleOwned()) {
            return true;
        }

        if (!validateRequiredField(fields.vehicleModel, '보유 차량명을 입력해주세요.')) {
            return false;
        }

        if (!validateVehicleYear()) {
            return false;
        }

        if (!validateDrivingDistance()) {
            return false;
        }

        return true;
    };

    const validatePasswordChange = () => {
        const currentPassword = getInputValue(fields.currentPassword);
        const newPassword = getInputValue(fields.newPassword);
        const newPasswordConfirm = getInputValue(fields.newPasswordConfirm);
        const isPasswordChangeRequested = Boolean(newPassword || newPasswordConfirm);

        if (!currentPassword) {
            return alertAndFocus('현재 비밀번호를 입력해주세요.', fields.currentPassword);
        }

        if (!isPasswordChangeRequested) {
            return true;
        }

        if (!newPassword || !newPasswordConfirm) {
            return alertAndFocus('새 비밀번호와 새 비밀번호 확인을 모두 입력해주세요.', fields.newPassword);
        }

        if (newPassword !== newPasswordConfirm) {
            return alertAndFocus('새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.', fields.newPasswordConfirm);
        }

        return true;
    };

    const prepareSubmit = () => {
        form.querySelectorAll('input').forEach((input) => {
            if (input.name === 'gender') {
                input.disabled = true;
                return;
            }

            input.disabled = false;
        });

        if (!isVehicleOwned()) {
            clearOwnedVehicleFields();
        }
    };

    const validateForm = () => {
        if (!validateBasicFields()) {
            return false;
        }

        if (!validateOwnedVehicleFields()) {
            return false;
        }

        if (!validatePasswordChange()) {
            return false;
        }

        return true;
    };

    hasVehicleInputs.forEach((input) => {
        input.addEventListener('change', () => {
            if (!isVehicleOwned()) {
                clearOwnedVehicleFields();
            }

            updateOwnedVehicleFieldsState();
        });
    });

    editModeButton.addEventListener('click', () => {
        saveInitialValues();
        setEditMode(true);
    });

    cancelButton.addEventListener('click', () => {
        restoreInitialValues();
        setEditMode(false);
    });

    form.addEventListener('submit', (event) => {
        if (!validateForm()) {
            event.preventDefault();
            return;
        }

        prepareSubmit();
    });

    saveInitialValues();
    setEditMode(false);
});