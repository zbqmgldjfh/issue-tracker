import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Loading from 'src/assets/loading.gif';
import { OAUTH_API_URL, SERVER_URL } from 'src/constants/constant';
import { fetchData } from 'src/utils/utils';
import { LoadingBox, LoadingText } from './LoadingPage.styled';

const parseOAuthCode = () => {
  const [_, code] = location.search.split('=');

  return code;
};

const getUrl = () => {
  const oauthCode = parseOAuthCode();
  const url = `${SERVER_URL}${OAUTH_API_URL}?code=${oauthCode}`;
  return url;
};

const getToken = async (url: string) => {
  console.log('fetch url :', url);
  const data = await fetchData(url);
  return data;
};

export default function LoadingPage() {
  const [token, setToken] = useState('');
  const navigate = useNavigate();
  const url = getUrl();

  useEffect(() => {
    const runLogin = async () => {
      const { token } = await getToken(url);
      if (token) {
        localStorage.setItem('token', JSON.stringify(token));
        setToken(token);
      }
    };

    runLogin();
  }, []);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      navigate(`/`);
    }
  }, [token]);
  getToken(url);

  return (
    <LoadingBox>
      <img src={Loading} alt="로딩중"></img>
      <LoadingText>Loading ...</LoadingText>
    </LoadingBox>
  );
}
