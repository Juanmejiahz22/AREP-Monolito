import { useState } from 'react';
import { MessageBox } from './MessageBox';

export function CreatePostForm({ isAuthenticated, onSubmit }) {
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const remaining = 140 - message.length;

  async function handleSubmit(event) {
    event.preventDefault();
    if (!message.trim()) {
      setError('Message is required.');
      return;
    }
    if (message.length > 140) {
      setError('Message must be at most 140 characters.');
      return;
    }
    setError('');
    await onSubmit(message);
    setMessage('');
  }

  return (
    <form className="panel" onSubmit={handleSubmit}>
      <h2>Create Post</h2>
      <textarea
        rows="4"
        value={message}
        onChange={(event) => setMessage(event.target.value)}
        placeholder="Write up to 140 characters"
        disabled={!isAuthenticated}
      />
      <p className={remaining < 0 ? 'counter invalid' : 'counter'}>
        Remaining characters: {remaining}
      </p>
      <MessageBox kind="error" text={error} />
      <button type="submit" disabled={!isAuthenticated}>Publish</button>
    </form>
  );
}
