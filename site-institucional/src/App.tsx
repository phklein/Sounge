import { BrowserRouter, Route, Routes } from "react-router-dom"

import { Home } from "./pages/Home"
import { PageNotFound } from "./pages/PageNotFound"
import { Login } from "./pages/Login"
import { Register } from "./pages/register/Register"
import { Premium} from "./pages/Premium"

import { MultiForm } from "./components/MultiForm"

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/page-not-found" element={<PageNotFound />} />
        <Route path="/register" element={<MultiForm />} />
        <Route path="/login" element={<Login />} />
        <Route path="/premium" element={<Premium />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App;
