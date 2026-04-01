'use strict';

document.addEventListener('DOMContentLoaded', () => {
    const wishlistGrid = document.getElementById('wishlistGrid');
    const wishlistEmpty = document.getElementById('wishlistEmpty');
    const wishlistMoreWrap = document.getElementById('wishlistMoreWrap');
    const wishlistMoreButton = document.getElementById('wishlistMoreButton');

    if (!wishlistGrid || !wishlistEmpty || !wishlistMoreWrap || !wishlistMoreButton) {
        return;
    }

    const DEFAULT_VISIBLE_COUNT = 3;
    let isExpanded = false;

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
        return await getWishlistFromServer();
    };

    const removeWishlistItem = async (wishlistId) => {
        await removeWishlistFromServer(wishlistId);
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

    wishlistMoreButton.addEventListener('click', async () => {
        isExpanded = !isExpanded;
        await renderWishlist();
    });

    renderWishlist().catch(() => {
        window.alert('관심 차량 정보를 불러오는 중 오류가 발생했습니다.');
    });
});