'use strict';

document.addEventListener('DOMContentLoaded', () => {
    const wishlistGrid = document.getElementById('wishlistGrid');
    const wishlistEmpty = document.getElementById('wishlistEmpty');
    const wishlistMoreWrap = document.getElementById('wishlistMoreWrap');
    const wishlistMoreButton = document.getElementById('wishlistMoreButton');
    const addSampleVehicleButton = document.getElementById('addSampleVehicleButton');
    const resetWishlistButton = document.getElementById('resetWishlistButton');

    if (!wishlistGrid || !wishlistEmpty || !wishlistMoreWrap || !wishlistMoreButton) {
        return;
    }

    const STORAGE_KEY = 'evcar-wishlist';
    const USE_TEST_DATA = false;
    const DEFAULT_VISIBLE_COUNT = 3;
    let isExpanded = false;

    const defaultWishlist = [
        {
            wishlistId: 1,
            brand: '현대',
            vehicleClass: '중형 SUV',
            modelName: '아이오닉 5',
            priceBasic: '5,200',
            imageUrl: '/images/ev_HYUNDAI_IONIQ5.png',
            detailUrl: '#'
        },
        {
            wishlistId: 2,
            brand: '기아',
            vehicleClass: '중형 SUV',
            modelName: 'EV6',
            priceBasic: '4,870',
            imageUrl: '/images/ev_KIA_EV6.png',
            detailUrl: '#'
        },
        {
            wishlistId: 3,
            brand: '현대',
            vehicleClass: '대형 SUV',
            modelName: '아이오닉 9',
            priceBasic: '6,700',
            imageUrl: '/images/ev_HYUNDAI_IONIQ9.png',
            detailUrl: '#'
        },
        {
            wishlistId: 4,
            brand: '기아',
            vehicleClass: '중형 SUV',
            modelName: 'EV3',
            priceBasic: '4,200',
            imageUrl: '/images/ev_KIA_EV3.png',
            detailUrl: '#'
        }
    ];

    const getPreviewUserId = () => {
        const params = new URLSearchParams(window.location.search);
        return params.get('previewUserId');
    };

    const buildApiUrl = (basePath) => {
        const previewUserId = getPreviewUserId();

        if (!previewUserId) {
            return basePath;
        }

        const query = new URLSearchParams({
            previewUserId: previewUserId
        });

        return `${basePath}?${query.toString()}`;
    };

    const formatPrice = (priceBasic) => {
        if (priceBasic === null || priceBasic === undefined || priceBasic === '') {
            return '-';
        }

        const numericPrice = Number(priceBasic);
        if (Number.isNaN(numericPrice)) {
            return `${priceBasic}`;
        }

        return `${numericPrice.toLocaleString()}만원`;
    };

    const escapeHtml = (value) => {
        return String(value ?? '')
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;');
    };

    const createCardHtml = (item) => {
        const brand = escapeHtml(item.brand);
        const vehicleClass = escapeHtml(item.vehicleClass);
        const modelName = escapeHtml(item.modelName);
        const imageUrl = escapeHtml(item.imageUrl);
        const detailUrl = escapeHtml(item.detailUrl);
        const wishlistId = escapeHtml(item.wishlistId);
        const priceText = escapeHtml(formatPrice(item.priceBasic));

        return `
            <article class="ev-wishlist-card" data-wishlist-id="${wishlistId}">
                <button type="button" class="ev-wishlist-favorite" aria-label="관심차량 삭제">♥</button>
                <div class="ev-wishlist-image-wrap">
                    <img class="ev-wishlist-image" src="${imageUrl}" alt="${modelName}">
                </div>
                <div class="ev-wishlist-body">
                    <p class="ev-wishlist-brand">${brand} | ${vehicleClass}</p>
                    <h2 class="ev-wishlist-model">${modelName}</h2>
                    <p class="ev-wishlist-price">${priceText}</p>
                    <div class="ev-wishlist-actions">
                        <a href="${detailUrl}" class="btn btn-ev-secondary">상세보기</a>
                        <button type="button" class="btn btn-ev-primary ev-delete-btn">삭제</button>
                    </div>
                </div>
            </article>
        `;
    };

    const renderEmptyState = (wishlist) => {
        const isEmpty = wishlist.length === 0;
        wishlistGrid.style.display = isEmpty ? 'none' : 'grid';
        wishlistEmpty.classList.toggle('ev-wishlist-empty--hidden', !isEmpty);
    };

    const renderMoreButton = (wishlist) => {
        const shouldShowMoreButton = wishlist.length > DEFAULT_VISIBLE_COUNT;
        wishlistMoreWrap.classList.toggle('ev-wishlist-more--hidden', !shouldShowMoreButton);

        if (shouldShowMoreButton) {
            wishlistMoreButton.textContent = isExpanded ? '접기' : '더보기';
        }
    };

    const bindEvents = () => {
        const deleteButtons = wishlistGrid.querySelectorAll('.ev-delete-btn');
        deleteButtons.forEach((button) => {
            button.addEventListener('click', async (event) => {
                const card = event.target.closest('.ev-wishlist-card');
                await handleDelete(card);
            });
        });

        const favoriteButtons = wishlistGrid.querySelectorAll('.ev-wishlist-favorite');
        favoriteButtons.forEach((button) => {
            button.addEventListener('click', async (event) => {
                const card = event.target.closest('.ev-wishlist-card');
                await handleDelete(card);
            });
        });
    };

    const renderWishlist = async () => {
        const wishlist = await getWishlist();
        const visibleWishlist = isExpanded ? wishlist : wishlist.slice(0, DEFAULT_VISIBLE_COUNT);

        wishlistGrid.innerHTML = visibleWishlist.map(createCardHtml).join('');
        renderEmptyState(wishlist);
        renderMoreButton(wishlist);
        bindEvents();
    };

    const handleDelete = async (card) => {
        if (!card) {
            return;
        }

        const wishlistId = card.dataset.wishlistId;

        if (!wishlistId) {
            window.alert('관심 차량 식별값이 없습니다.');
            return;
        }

        const confirmed = window.confirm('관심차량에서 삭제하시겠습니까?');
        if (!confirmed) {
            return;
        }

        await removeWishlistItem(wishlistId);

        const updatedWishlist = await getWishlist();
        if (updatedWishlist.length <= DEFAULT_VISIBLE_COUNT) {
            isExpanded = false;
        }

        await renderWishlist();
    };

    const getWishlistFromLocal = () => {
        const stored = localStorage.getItem(STORAGE_KEY);

        if (!stored) {
            localStorage.setItem(STORAGE_KEY, JSON.stringify(defaultWishlist));
            return defaultWishlist;
        }

        try {
            return JSON.parse(stored);
        } catch (error) {
            localStorage.setItem(STORAGE_KEY, JSON.stringify(defaultWishlist));
            return defaultWishlist;
        }
    };

    const saveWishlistToLocal = (wishlist) => {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(wishlist));
    };

    const addWishlistToLocal = (item) => {
        const wishlist = getWishlistFromLocal();
        const exists = wishlist.some((wishlistItem) => wishlistItem.modelName === item.modelName);

        if (exists) {
            window.alert('이미 관심차량에 등록된 차량입니다.');
            return;
        }

        wishlist.push(item);
        saveWishlistToLocal(wishlist);
    };

    const removeWishlistFromLocal = (wishlistId) => {
        const wishlist = getWishlistFromLocal();
        const filteredWishlist = wishlist.filter((item) => String(item.wishlistId) !== String(wishlistId));
        saveWishlistToLocal(filteredWishlist);
    };

    const getWishlistFromServer = async () => {
        const response = await fetch(buildApiUrl('/mypage/wishlist/api'), {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('관심 차량 목록 조회에 실패했습니다.');
        }

        return await response.json();
    };

    const removeWishlistFromServer = async (wishlistId) => {
        const response = await fetch(buildApiUrl('/mypage/wishlist/delete'), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                wishlistId: String(wishlistId)
            })
        });

        if (!response.ok) {
            throw new Error('관심 차량 삭제에 실패했습니다.');
        }
    };

    const getWishlist = async () => {
        if (USE_TEST_DATA) {
            return getWishlistFromLocal();
        }

        return await getWishlistFromServer();
    };

    const removeWishlistItem = async (wishlistId) => {
        if (USE_TEST_DATA) {
            removeWishlistFromLocal(wishlistId);
            return;
        }

        await removeWishlistFromServer(wishlistId);
    };

    const addSampleWishlistItem = async () => {
        if (!USE_TEST_DATA) {
            window.alert('개발용 테스트 모드가 아닙니다.');
            return;
        }

        addWishlistToLocal({
            wishlistId: Date.now(),
            brand: '현대',
            vehicleClass: '대형 SUV',
            modelName: '아이오닉 9',
            priceBasic: '6,700',
            imageUrl: '/images/ev_HYUNDAI_IONIQ9.png',
            detailUrl: '#'
        });

        await renderWishlist();
    };

    if (wishlistMoreButton) {
        wishlistMoreButton.addEventListener('click', async () => {
            isExpanded = !isExpanded;
            await renderWishlist();
        });
    }

    if (addSampleVehicleButton) {
        addSampleVehicleButton.addEventListener('click', async () => {
            await addSampleWishlistItem();
        });
    }

    if (resetWishlistButton) {
        resetWishlistButton.addEventListener('click', async () => {
            if (!USE_TEST_DATA) {
                window.alert('개발용 테스트 모드에서만 초기화할 수 있습니다.');
                return;
            }

            localStorage.removeItem(STORAGE_KEY);
            isExpanded = false;
            await renderWishlist();
        });
    }

    renderWishlist().catch(() => {
        window.alert('관심 차량 정보를 불러오는 중 오류가 발생했습니다.');
    });
});