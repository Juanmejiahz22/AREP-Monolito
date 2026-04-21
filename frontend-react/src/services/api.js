import { config } from '../config';

async function readJson(response) {
  const isJson = response.headers.get('content-type')?.includes('application/json');
  return isJson ? response.json() : response.text();
}

async function request(path, options = {}) {
  const response = await fetch(`${config.apiBaseUrl}${path}`, options);
  const payload = await readJson(response);
  if (!response.ok) {
    const message = payload?.message || 'Request failed';
    throw new Error(message);
  }
  return payload;
}

export async function getFeed() {
  return request('/api/posts');
}

export async function getProfile(token) {
  return request('/api/me', {
    headers: { Authorization: `Bearer ${token}` }
  });
}

export async function createPost(token, message) {
  return request('/api/posts', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    },
    body: JSON.stringify({ message })
  });
}
