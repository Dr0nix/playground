(function(){
    const ready = (fn) => document.readyState !== 'loading'
        ? fn() : document.addEventListener('DOMContentLoaded', fn);

    ready(() => {
        const root = document.querySelector('.sb');
        if (!root) return;

        root.querySelectorAll('.title[role="button"]').forEach(title => {
            const id = title.getAttribute('aria-controls');
            const list = id ? document.getElementById(id) : title.nextElementSibling;
            const open = title.getAttribute('aria-expanded') !== 'false';
            if (list) list.classList.toggle('is-collapsed', !open);
        });

        root.addEventListener('click', (e) => {
            const title = e.target.closest('.title[role="button"]');
            if (!title) return;
            const id = title.getAttribute('aria-controls');
            const list = id ? document.getElementById(id) : title.nextElementSibling;
            const open = title.getAttribute('aria-expanded') === 'true';
            title.setAttribute('aria-expanded', String(!open));
            if (list) list.classList.toggle('is-collapsed', open);
        });

        root.addEventListener('keydown', (e) => {
            if (e.key === 'Enter' || e.key === ' ') { e.preventDefault(); e.target.click(); }
        });
    });
})();
