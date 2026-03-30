const regionData = {
    "서울": ["강남구", "서초구", "중구", "종로구"],
    "경기": ["수원시", "성남시", "고양시", "용인시"],
    "부산": ["해운대구", "수영구"]
};

function changeSigungu() {
    const sido = document.getElementById('sido').value;
    const sigungu = document.getElementById('sigungu');

    sigungu.innerHTML = '<option value="">시/군/구 선택</option>';

    if (!sido) return;

    regionData[sido].forEach(item => {
        const option = document.createElement('option');
        option.value = item;
        option.textContent = item;
        sigungu.appendChild(option);
    });
}