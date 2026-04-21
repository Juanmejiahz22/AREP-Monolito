export function ProfilePanel({ profile, onLoad, isAuthenticated, loading }) {
  return (
    <section className="panel">
      <div className="section-header">
        <h2>Profile</h2>
        {isAuthenticated && <button onClick={onLoad} disabled={loading}>Refresh</button>}
      </div>
      {!isAuthenticated && <p>Login to load the protected <code>/api/me</code> endpoint.</p>}
      {profile && (
        <dl className="profile-grid">
          <dt>Subject</dt><dd>{profile.subject}</dd>
          <dt>Name</dt><dd>{profile.name}</dd>
          <dt>Email</dt><dd>{profile.email || 'not present in token'}</dd>
        </dl>
      )}
    </section>
  );
}
