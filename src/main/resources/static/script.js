/*
 * Copyright (c) 2024 rIvorraLl
 *
 * Licensed under the GNU General Public License, Version 2 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * CineStash Frontend Logic
 * Handles UI interactions, API communication, and data visualization.
 */

let currentPage = 0;
let currentSearch = '';
let currentSort = 'date';
let statsChart = null;

/**
 * Toggles the visibility of the movie entry form.
 */
function toggleForm() {
    const formSection = document.getElementById('form-section');
    const toggleBtn = document.getElementById('toggle-form-btn');

    if (formSection.style.display === 'none') {
        formSection.style.display = 'block';
        toggleBtn.innerText = '✕ Close Form';
        toggleBtn.style.backgroundColor = '#333';
    } else {
        formSection.style.display = 'none';
        toggleBtn.innerText = '+ Add New Entry';
        toggleBtn.style.backgroundColor = '#28a745';
    }
}

/**
 * Switches between the Diary view and the Analytics/Stats view.
 * @param {string} view - The view name ('diary' or 'stats').
 */
function showView(view) {
    if (view === 'stats') {
        document.getElementById('diary-view').style.display = 'none';
        document.getElementById('stats-view').style.display = 'block';
        document.getElementById('nav-stats').classList.add('active-nav');
        document.getElementById('nav-diary').classList.remove('active-nav');
        loadStats();
    } else {
        document.getElementById('diary-view').style.display = 'block';
        document.getElementById('stats-view').style.display = 'none';
        document.getElementById('nav-diary').classList.add('active-nav');
        document.getElementById('nav-stats').classList.remove('active-nav');
        loadMovies(currentSort, currentPage);
    }
}

/**
 * Updates the CSS classes for sort buttons based on the active selection.
 * @param {string} activeSort - The current active sorting criteria.
 */
function updateSortVisuals(activeSort) {
    const dateBtn = document.getElementById('btn-sort-date');
    const starsBtn = document.getElementById('btn-sort-stars');

    if (!dateBtn || !starsBtn) return;

    dateBtn.classList.remove('active-sort');
    starsBtn.classList.remove('active-sort');

    if (activeSort === 'date') {
        dateBtn.classList.add('active-sort');
    } else if (activeSort === 'stars') {
        starsBtn.classList.add('active-sort');
    }
}

/**
 * Fetches and displays the list of movies from the backend.
 * @param {string} sortBy - The field to sort by.
 * @param {number} page - The page index to load.
 */
async function loadMovies(sortBy = currentSort, page = 0) {
    currentSort = sortBy;
    currentPage = page;
    updateSortVisuals(sortBy);

    const res = await fetch(`/api/movies?q=${currentSearch}&page=${page}&sort=${sortBy}`);
    const data = await res.json();
    renderMovies(data.content);
    renderPagination(data);
}

/**
 * Renders movie cards into the DOM.
 * @param {Array} movies - The list of movie DTOs.
 */
function renderMovies(movies) {
    const list = document.getElementById('movie-list');
    list.innerHTML = movies.map(m => `
        <div class="movie-card">
            <img src="${m.imageData || 'https://via.placeholder.com/80x120?text=Poster'}"
                 class="poster-thumb" onclick="openLightbox('${m.imageData}')">
            <div style="flex:1">
                <button style="float:right; background:none; color:#555; padding:0; font-size:1.5rem;" onclick="deleteMovie(${m.id})">&times;</button>
                <h3>${m.title} <span style="color:#f1c40f">★ ${m.stars}</span></h3>
                <p><strong>${m.director}</strong> | ${m.dateOfView}</p>
                <p style="color:#aaa; font-style:italic">"${m.review}"</p>
                <small style="color:#666">Cast: ${m.mainActors}</small>
            </div>
        </div>
    `).join('');
}

/**
 * Converts a File object to a Base64 encoded string.
 * @param {File} file
 * @returns {Promise<string>}
 */
const toBase64 = file => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
});

// FORM SUBMISSION EVENT LISTENER
document.getElementById('movie-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    let base64Image = null;
    const file = document.getElementById('imageFile').files[0];
    if (file) base64Image = await toBase64(file);

    const movie = {
        title: document.getElementById('title').value,
        director: document.getElementById('director').value,
        mainActors: document.getElementById('actors').value,
        dateOfView: document.getElementById('viewDate').value,
        review: document.getElementById('review').value,
        stars: parseInt(document.getElementById('stars').value),
        imageData: base64Image
    };

    const res = await fetch('/api/movies', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(movie)
    });

    if (res.ok) {
        document.getElementById('movie-form').reset();
        toggleForm();
        loadMovies();
    }
});

/**
 * Loads and displays the movie statistics dashboard.
 */
async function loadStats() {
    const res = await fetch('/api/movies/stats');
    const stats = await res.json();
    document.getElementById('stat-total').innerText = stats.totalMovies;
    document.getElementById('stat-avg').innerText = stats.averageRating;
    document.getElementById('stat-director').innerText = stats.topDirector;
    document.getElementById('stat-actor').innerText = stats.topActor;
    renderChart(stats.moviesPerMonth);
}

/**
 * Renders the watch trends chart using Chart.js.
 * @param {Object} dataMap - Map of year-month to movie count.
 */
function renderChart(dataMap) {
    const ctx = document.getElementById('viewingChart').getContext('2d');
    if (statsChart) statsChart.destroy();
    statsChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: Object.keys(dataMap),
            datasets: [{
                label: 'Movies per Month',
                data: Object.values(dataMap),
                borderColor: '#e50914',
                backgroundColor: 'rgba(229, 9, 20, 0.1)',
                fill: true,
                tension: 0.3
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: { beginAtZero: true, grid: { color: '#333' }, ticks: { color: '#fff' } },
                x: { grid: { color: '#333' }, ticks: { color: '#fff' } }
            }
        }
    });
}

/**
 * Triggers a debounced search based on the user input.
 */
function debounceSearch() {
    clearTimeout(window.searchTimeout);
    window.searchTimeout = setTimeout(() => {
        currentSearch = document.getElementById('searchInput').value;
        loadMovies(currentSort, 0);
    }, 400);
}

/**
 * Sends a DELETE request to the backend for a specific movie ID.
 * @param {number} id
 */
async function deleteMovie(id) {
    if (confirm('Delete permanently?')) {
        await fetch(`/api/movies/${id}`, { method: 'DELETE' });
        loadMovies();
    }
}

/**
 * Opens the poster image in a full-screen lightbox.
 * @param {string} img - Base64 image data.
 */
function openLightbox(img) {
    if (!img) return;
    document.getElementById('lightbox-img').src = img;
    document.getElementById('lightbox').style.display = 'flex';
}

/**
 * Renders pagination buttons.
 * @param {Object} data - Paginated data from the backend.
 */
function renderPagination(data) {
    const container = document.getElementById('pagination-controls');
    container.innerHTML = '';
    if (data.totalPages <= 1) return;
    for (let i = 0; i < data.totalPages; i++) {
        const btn = document.createElement('button');
        btn.innerText = i + 1;
        btn.className = i === currentPage ? 'active' : '';
        btn.onclick = () => loadMovies(currentSort, i);
        container.appendChild(btn);
    }
}

/**
 * Exports all diary data as a downloadable JSON file.
 */
async function exportData() {
    const res = await fetch('/api/movies/export');
    const data = await res.json();
    const blob = new Blob([JSON.stringify(data)], { type: 'application/json' });
    const a = document.createElement('a');
    a.href = URL.createObjectURL(blob);
    a.download = 'cinestash-backup.json';
    a.click();
}

/**
 * Imports a JSON file containing movie entries.
 * @param {Event} event - File input change event.
 */
async function importData(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = async (e) => {
        const movies = JSON.parse(e.target.result);
        await fetch('/api/movies/import', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(movies)
        });
        loadMovies();
    };
    reader.readAsText(file);
}

// Initial movie load
loadMovies('date', 0);
