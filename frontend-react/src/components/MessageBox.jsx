export function MessageBox({ kind = 'info', text }) {
  if (!text) return null;
  return <p className={`message message-${kind}`}>{text}</p>;
}
