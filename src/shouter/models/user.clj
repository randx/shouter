(ns shouter.models.user
  (:require
    [clojure.java.jdbc :as sql]
    [cemerick.friend.credentials :as creds]))

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/shouter"))

(defn create [username password]
  (sql/insert! spec :users [:username :encrypted_password] [username (creds/hash-bcrypt password)]))

(defn find-by-username [name]
  (first (sql/query spec ["select * from users where username = ?" name])))
