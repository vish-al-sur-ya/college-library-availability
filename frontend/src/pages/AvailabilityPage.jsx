import { useState } from "react";
import { api } from "../api";

const AvailabilityPage = () => {
  const [bookId, setBookId] = useState("");
  const [result, setResult] = useState(null);
  const [error, setError] = useState("");

  const check = async () => {
    if (!bookId) return;
    try {
      setError("");
      const data = await api.checkAvailability(bookId);
      setResult(data);
    } catch (e) {
      setError(e.message);
      setResult(null);
    }
  };

  return (
    <section className="card">
      <h2>Availability Results</h2>
      <div className="row">
        <input
          type="number"
          min="1"
          value={bookId}
          onChange={(e) => setBookId(e.target.value)}
          placeholder="Enter Book ID"
        />
        <button type="button" onClick={check}>Check Availability</button>
      </div>

      {error && <p className="error">{error}</p>}
      {result && (
        <div className="card nested">
          <p><strong>Title:</strong> {result.title}</p>
          <p><strong>Status:</strong> {result.status}</p>
          <p><strong>Available Copies:</strong> {result.availableCopies}</p>
          <p><strong>Shelf:</strong> {result.shelfNumber}</p>
        </div>
      )}
    </section>
  );
};

export default AvailabilityPage;
