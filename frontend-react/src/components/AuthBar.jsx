import { useAuth0 } from '@auth0/auth0-react';

export function AuthBar() {
  const { isAuthenticated, loginWithRedirect, logout, user } = useAuth0();

  if (!isAuthenticated) {
    return (
      <div className="panel auth-panel">
        <h2>Authentication</h2>
        <p>Login is handled with the Auth0 React SDK</p>
        <button onClick={() => loginWithRedirect()}>Login with Auth0</button>
      </div>
    );
  }

  return (
    <div className="panel auth-panel">
      <h2>Authentication</h2>
      <p>Signed in as <strong>{user?.name || user?.nickname || user?.email}</strong></p>
      <button onClick={() => logout({ logoutParams: { returnTo: globalThis.location.origin } })}>
        Logout
      </button>
    </div>
  );
}
