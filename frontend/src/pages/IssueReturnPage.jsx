import { useState } from "react";
import { api } from "../api";

const IssueReturnPage = () => {
  const [bookId, setBookId] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const runAction = async (action) => {
    if (!bookId) return;
    try {
      setError("");
      const actions = {
        issue: api.issueBook,
        return: api.returnBook,
        reserve: api.reserveBook,
      };
      const data = await actions[action](bookId);
      setMessage(`Book \"${data.title}\" is now ${data.status}. Available copies: ${data.availableCopies}`);
    } catch (e) {
      setError(e.message);
      setMessage("");
    }
  };

  return (
    <section className="card">
      <h2>Issue / Return / Reserve Book</h2>
      <div className="row">
        <input
          type="number"
          min="1"
          value={bookId}
          onChange={(e) => setBookId(e.target.value)}
          placeholder="Enter Book ID"
        />
        <button type="button" onClick={() => runAction("issue")}>Issue</button>
        <button type="button" onClick={() => runAction("return")}>Return</button>
        <button type="button" onClick={() => runAction("reserve")}>Reserve</button>
      </div>
      {message && <p className="success">{message}</p>}
      {error && <p className="error">{error}</p>}
    </section>
  );
};

export default IssueReturnPage;
