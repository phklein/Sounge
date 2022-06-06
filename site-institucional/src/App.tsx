import { BrowserRouter, Route, Routes } from "react-router-dom";

import { Home } from "./pages/Home";
import { PageNotFound } from "./pages/PageNotFound";
import { Login } from "./pages/Login";
import UserProfile from "./pages/profile/UserProfile";
import BandProfile from "./pages/profile/BandProfile";
import { Match } from "./pages/match/Match";
import { Premium } from "./pages/Premium";

import { MultiForm } from "./components/MultiForm";
import { MatchMock } from "./pages/match/MatchMock";
import { Interaction } from "./components/Interaction"

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/page-not-found" element={<PageNotFound />} />
        <Route path="/register" element={<MultiForm />} />
        <Route path="/login" element={<Login />} />
        <Route path="/profile/:id" element={<UserProfile />} />
        <Route path="/band/:id" element={<BandProfile />} />
        <Route path="/match/:id" element={<Match />} />
        <Route path="/match-mock" element={<MatchMock />} />
        <Route path="/premium" element={<Premium />} />
        <Route path="/interaction" element={<Interaction />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
