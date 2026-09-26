import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { api } from "../api";

const BookDetailsPage = () => {
  const { id } = useParams();
  const [book, setBook] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchBook = async () => {
      try {
        setError("");
        const data = await api.getBookById(id);
        setBook(data);
      } catch (e) {
        setError(e.message);
      }
    };

    fetchBook();
  }, [id]);

  if (error) return <p className="error">{error}</p>;
  if (!book) return <p>Loading...</p>;

  return (
    <section className="card">
      <h2>{book.title}</h2>
      <div className="details-grid">
        <p><strong>Author:</strong> {book.author}</p>
        <p><strong>ISBN:</strong> {book.isbn}</p>
        <p><strong>Category:</strong> {book.category}</p>
        <p><strong>Publisher:</strong> {book.publisher}</p>
        <p><strong>Edition:</strong> {book.edition}</p>
        <p><strong>Shelf/Rack:</strong> {book.shelfNumber}</p>
        <p><strong>Total Copies:</strong> {book.totalCopies}</p>
        <p><strong>Available Copies:</strong> {book.availableCopies}</p>
        <p><strong>Status:</strong> {book.status}</p>
      </div>
    </section>
  );
};

export default BookDetailsPage;
