const API_BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

const request = async (path, options = {}) => {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    headers: { "Content-Type": "application/json", ...(options.headers || {}) },
    ...options,
  });

  if (!response.ok) {
    let message = "Request failed";
    try {
      const body = await response.json();
      message = body.message || message;
    } catch {
      // ignore invalid json
    }
    throw new Error(message);
  }

  if (response.status === 204) {
    return null;
  }
  return response.json();
};

export const api = {
  getBooks: (q = "") => request(`/books${q ? `?q=${encodeURIComponent(q)}` : ""}`),
  getBookById: (id) => request(`/books/${id}`),
  createBook: (payload) => request("/books", { method: "POST", body: JSON.stringify(payload) }),
  updateBook: (id, payload) => request(`/books/${id}`, { method: "PUT", body: JSON.stringify(payload) }),
  deleteBook: (id) => request(`/books/${id}`, { method: "DELETE" }),
  issueBook: (id) => request(`/books/${id}/issue`, { method: "PUT" }),
  returnBook: (id) => request(`/books/${id}/return`, { method: "PUT" }),
  reserveBook: (id) => request(`/books/${id}/reserve`, { method: "PUT" }),
  checkAvailability: (id) => request(`/books/${id}/availability`),
};
