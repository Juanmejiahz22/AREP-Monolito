import { useEffect, useState } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import { AuthBar } from './components/AuthBar';
import { CreatePostForm } from './components/CreatePostForm';
import { Feed } from './components/Feed';
import { MessageBox } from './components/MessageBox';
import { ProfilePanel } from './components/ProfilePanel';
import { createPost, getFeed, getProfile } from './services/api';
import { config } from './config';
import './styles.css';

export default function App() {
  const { isAuthenticated, getAccessTokenSilently, isLoading } = useAuth0();
  const [posts, setPosts] = useState([]);
  const [profile, setProfile] = useState(null);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loadingFeed, setLoadingFeed] = useState(false);
  const [loadingProfile, setLoadingProfile] = useState(false);

  useEffect(() => {
    loadFeed();
  }, []);

  async function loadFeed() {
    setLoadingFeed(true);
    setError('');
    try {
      setPosts(await getFeed());
    } catch (err) {
      setError(err.message);
    } finally {
      setLoadingFeed(false);
    }
  }

  async function loadProfile() {
    setLoadingProfile(true);
    setError('');
    try {
      const token = await getAccessTokenSilently();
      setProfile(await getProfile(token));
    } catch (err) {
      setError(err.message);
    } finally {
      setLoadingProfile(false);
    }
  }

  async function handleCreatePost(message) {
    setError('');
    setSuccess('');
    try {
      const token = await getAccessTokenSilently();
      await createPost(token, message);
      setSuccess('Post created successfully.');
      await loadFeed();
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <main className="app-shell">
      <header className="hero">
        <h1>{config.appTitle}</h1>
      </header>

      <MessageBox kind="error" text={error} />
      <MessageBox kind="success" text={success} />
      {isLoading && <MessageBox text="Checking Auth0 session..." />}

      <div className="layout-grid">
        <aside className="sidebar">
          <AuthBar />
          <ProfilePanel
            profile={profile}
            onLoad={loadProfile}
            isAuthenticated={isAuthenticated}
            loading={loadingProfile}
          />
        </aside>
        
        <section className="main-content">
          <CreatePostForm
            isAuthenticated={isAuthenticated}
            onSubmit={handleCreatePost}
          />
          <Feed posts={posts} onRefresh={loadFeed} loading={loadingFeed} />
        </section>
      </div>
    </main>
  );
}