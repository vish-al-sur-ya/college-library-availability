import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { api } from "../api";

const initialState = {
  title: "",
  author: "",
  isbn: "",
  category: "",
  publisher: "",
  edition: "",
  shelfNumber: "",
  totalCopies: 1,
  availableCopies: 1,
};

const AddEditBookPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [form, setForm] = useState(initialState);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!id) return;
    const load = async () => {
      try {
        const data = await api.getBookById(id);
        setForm({
          title: data.title,
          author: data.author,
          isbn: data.isbn,
          category: data.category,
          publisher: data.publisher,
          edition: data.edition,
          shelfNumber: data.shelfNumber,
          totalCopies: data.totalCopies,
          availableCopies: data.availableCopies,
        });
      } catch (e) {
        setError(e.message);
      }
    };
    load();
  }, [id]);

  const submit = async (event) => {
    event.preventDefault();
    try {
      setError("");
      const payload = {
        ...form,
        totalCopies: Number(form.totalCopies),
        availableCopies: Number(form.availableCopies),
      };
      if (id) {
        await api.updateBook(id, payload);
      } else {
        await api.createBook(payload);
      }
      navigate("/admin");
    } catch (e) {
      setError(e.message);
    }
  };

  const setField = (name, value) => setForm((current) => ({ ...current, [name]: value }));

  return (
    <section className="card">
      <h2>{id ? "Edit Book" : "Add Book"}</h2>
      {error && <p className="error">{error}</p>}
      <form className="form-grid" onSubmit={submit}>
        {Object.keys(initialState).map((key) => (
          <label key={key}>
            {key}
            <input
              type={key.includes("Copies") ? "number" : "text"}
              min={key.includes("Copies") ? "0" : undefined}
              required
              value={form[key]}
              onChange={(e) => setField(key, e.target.value)}
            />
          </label>
        ))}
        <button type="submit">{id ? "Update" : "Create"}</button>
      </form>
    </section>
  );
};

export default AddEditBookPage;
