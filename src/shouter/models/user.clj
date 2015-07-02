(ns shouter.models.user
  (:require [clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/shouter"))

(def all {"admin" {:username "admin"
                   :password "$2a$10$NhVM.Ojpst0vRvMcME0NRu4ptJnlD2VyhSU4Af3rPq0Utnj6pbxvm"
                   :roles #{::admin}}
          "dave" {:username "dave"
                  :password "$2a$10$NhVM.Ojpst0vRvMcME0NRu4ptJnlD2VyhSU4Af3rPq0Utnj6pbxvm"
                  :roles #{::user}}})
