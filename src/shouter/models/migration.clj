(ns shouter.models.migration
  (:require [clojure.java.jdbc :as sql]
            [shouter.models.shout :as shout]))

(defn migrated? []
  (-> (sql/query shout/spec
                 [(str "select count(*) from information_schema.tables")])
      first :count
      (== 2)))

(defn migrate []
  (when (not (migrated?))
    (print "Creating database structure...") (flush)

    (if (-> (sql/query shout/spec
                       [(str "select count(*) from information_schema.tables "
                             "where table_name='shouts'")])
            first :count pos?)
      ()
      (sql/db-do-commands shout/spec
                          (sql/create-table-ddl
                            :shouts
                            [:id :serial "PRIMARY KEY"]
                            [:body :varchar "NOT NULL"]
                            [:created_at :timestamp
                             "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])))

    (if (-> (sql/query shout/spec
                       [(str "select count(*) from information_schema.tables "
                             "where table_name='users'")])
            first :count pos?)
      ()
      (sql/db-do-commands shout/spec
                          (sql/create-table-ddl
                            :users
                            [:id :serial "PRIMARY KEY"]
                            [:username :varchar "NOT NULL"]
                            [:encrypted_password :varchar "NOT NULL"]
                            [:created_at :timestamp
                             "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])))
    (println " done")))
