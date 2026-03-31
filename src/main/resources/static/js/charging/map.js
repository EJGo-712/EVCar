'use strict';

// 지도 생성
const mapContainer = document.getElementById('map');

const map = new kakao.maps.Map(mapContainer, {
    center: new kakao.maps.LatLng(37.5665, 126.9780),
    level: 7
});

// 🔥 마커 저장 배열
let markers = [];

// 🔥 기존 마커 제거
function clearMarkers() {
    markers.forEach(marker => marker.setMap(null));
    markers = [];
}

// 🔥 버튼 클릭 이벤트
document.getElementById('searchBtn').addEventListener('click', () => {

    const sido = document.getElementById('sido').value;
    const sigungu = document.getElementById('sigungu').value;

    if (!sido || !sigungu) {
        alert("지역 선택해라");
        return;
    }

    fetch(`/charging/region?sido=${sido}&sigungu=${sigungu}`)
        .then(res => res.json())
        .then(data => {

            console.log("지역 데이터:", data);

            clearMarkers();

            if (data.length === 0) {
                alert("데이터 없음");
                return;
            }

            // 🔥 지도 bounds (자동 이동용)
            const bounds = new kakao.maps.LatLngBounds();

            data.forEach(station => {

                const lat = station.lat;
                const lng = station.lng;

                if (!lat || !lng) return;

                const position = new kakao.maps.LatLng(lat, lng);

                // 🔥 bounds 추가
                bounds.extend(position);

                const marker = new kakao.maps.Marker({
                    map: map,
                    position: position
                });

                markers.push(marker);

                const infowindow = new kakao.maps.InfoWindow({
                    content: `<div style="padding:5px;font-size:12px;">${station.stationName}</div>`
                });

                kakao.maps.event.addListener(marker, 'click', function () {
                    infowindow.open(map, marker);
                });
            });

            // 🔥🔥🔥 핵심: 지도 이동
            map.setBounds(bounds);

        })
        .catch(err => console.error(err));
});