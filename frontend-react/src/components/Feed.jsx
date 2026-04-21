export function Feed({ posts, onRefresh, loading }) {
  return (
    <section className="panel">
      <div className="section-header">
        <h2>Public Feed</h2>
        <button onClick={onRefresh} disabled={loading}>Refresh</button>
      </div>
      {posts.length === 0 && <p>No posts yet.</p>}
      <ul className="feed-list">
        {posts.map((post) => (
          <li key={post.id} className="feed-item">
            <div className="feed-meta">
              <strong>{post.authorName}</strong>
              <span>{new Date(post.createdAt).toLocaleString()}</span>
            </div>
            <p>{post.message}</p>
          </li>
        ))}
      </ul>
    </section>
  );
}
