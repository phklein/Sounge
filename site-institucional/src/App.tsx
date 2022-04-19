import { BrowserRouter, Route, Routes } from "react-router-dom"
import { MultiForm } from "./components/MultiForm"

import { Home } from "./pages/Home"
import { Login } from "./pages/Login"
import { PageNotFound } from "./pages/PageNotFound"
import { Register } from "./pages/Register"

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/page-not-found" element={<PageNotFound />} />
        <Route path="/register" element={<MultiForm />} />
        <Route path="/login" element={<Login />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App;
