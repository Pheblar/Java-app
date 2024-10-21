import logo from './logo.svg';
import './App.css';
import PrivateArea from "./components/private-area";
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import CreatePostForm from "./components/add-item";

const App = () =>  {
  return (
      <BrowserRouter>
        <Routes>
          <Route path={'/'} element={<PrivateArea/>}></Route>
          <Route path={'/create-post'} element={<CreatePostForm/>}></Route>
        </Routes>
      </BrowserRouter>
  );
}

export default App;
