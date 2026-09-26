import { useEffect, useState } from "react";
import { api } from "../api";
import BookTable from "../components/BookTable";

const BookSearchPage = () => {
  const [query, setQuery] = useState("");
  const [books, setBooks] = useState([]);
  const [error, setError] = useState("");

  const fetchBooks = async (search = "") => {
    try {
      setError("");
      const data = await api.getBooks(search);
      setBooks(data);
    } catch (e) {
      setError(e.message);
    }
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  return (
    <section className="card">
      <h2>Book Search</h2>
      <form
        className="row"
        onSubmit={(event) => {
          event.preventDefault();
          fetchBooks(query);
        }}
      >
        <input
          value={query}
          onChange={(event) => setQuery(event.target.value)}
          placeholder="Search by title, author, ISBN, category"
        />
        <button type="submit">Search</button>
      </form>
      {error && <p className="error">{error}</p>}
      <BookTable books={books} />
    </section>
  );
};

export default BookSearchPage;
