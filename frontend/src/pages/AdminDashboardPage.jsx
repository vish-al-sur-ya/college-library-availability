import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../api";
import BookTable from "../components/BookTable";

const AdminDashboardPage = () => {
  const [books, setBooks] = useState([]);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const loadBooks = async () => {
    try {
      setError("");
      const data = await api.getBooks();
      setBooks(data);
    } catch (e) {
      setError(e.message);
    }
  };

  const onDelete = async (id) => {
    if (!confirm("Delete this book?")) return;
    try {
      await api.deleteBook(id);
      loadBooks();
    } catch (e) {
      setError(e.message);
    }
  };

  useEffect(() => {
    loadBooks();
  }, []);

  return (
    <section className="card">
      <h2>Admin Dashboard</h2>
      {error && <p className="error">{error}</p>}
      <BookTable
        books={books}
        showActions
        onEdit={(id) => navigate(`/admin/edit/${id}`)}
        onDelete={onDelete}
      />
    </section>
  );
};

export default AdminDashboardPage;
