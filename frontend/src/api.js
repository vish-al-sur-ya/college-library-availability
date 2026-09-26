const request = async (url, options = {}) => {
  const response = await fetch(url, {
    headers: { 'Content-Type': 'application/json' },
    ...options
  });
  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || 'Something went wrong');
  }
  return response.status === 204 ? null : response.json();
};

export const getBooks = (search = '') => request(`/api/books${search ? `?search=${encodeURIComponent(search)}` : ''}`);
export const createBook = (book) => request('/api/books', { method: 'POST', body: JSON.stringify(book) });
export const updateBook = (id, book) => request(`/api/books/${id}`, { method: 'PUT', body: JSON.stringify(book) });
export const deleteBook = (id) => request(`/api/books/${id}`, { method: 'DELETE' });
export const issueBook = (id) => request(`/api/books/${id}/issue`, { method: 'POST' });
export const returnBook = (id) => request(`/api/books/${id}/return`, { method: 'POST' });
