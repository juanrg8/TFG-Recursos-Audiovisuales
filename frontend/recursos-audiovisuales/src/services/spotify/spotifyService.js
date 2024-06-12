// src/services/spotifyService.js
import axios from 'axios';

const CLIENT_ID = process.env.VUE_APP_SPOTIFY_CLIENT_ID;
const CLIENT_SECRET = process.env.VUE_APP_SPOTIFY_CLIENT_SECRET;
const TOKEN_URL = 'https://accounts.spotify.com/api/token';
const SEARCH_URL = 'https://api.spotify.com/v1/search';

let accessToken = null;

// Añadir console.log para verificar las variables de entorno
console.log('Client ID:', CLIENT_ID);
console.log('Client Secret:', CLIENT_SECRET);

const getAccessToken = async () => {
  if (!accessToken) {
    const authResponse = await axios.post(TOKEN_URL, new URLSearchParams({
      grant_type: 'client_credentials'
    }), {
      headers: {
        'Authorization': 'Basic ' + btoa(CLIENT_ID + ':' + CLIENT_SECRET),
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    });
    accessToken = authResponse.data.access_token;
  }
  return accessToken;
};

export const searchSpotify = async (query, type) => {
  const token = await getAccessToken();
  const response = await axios.get(`${SEARCH_URL}?q=${query}&type=${type}`, {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
  return response.data;
};
