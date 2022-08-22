em++

-I./src/app/pdm-crypt-module/src/include
-I./src/app/pdm-crypt-module/src/lib

-I./src/app/pdm-crypt-module/src/lib/wasm
-I./src/app/pdm-crypt-module/src/lib/cpp-mmf

-I./src/app/pdm-crypt-module/src/lib/poly1305-donna-master

-I./src/app/pdm-crypt-module/src/lib/ecc

-I./src/app/pdm-crypt-module/src/lib/scrypt/include

-I./src/app/pdm-crypt-module/src/lib/scrypt

./src/app/pdm-crypt-module/src/lib/cc20_file.cpp

./src/app/pdm-crypt-module/src/lib/sha3.cpp

./src/app/pdm-crypt-module/src/lib/cpp-mmf/memory_mapped_file.cpp

./src/app/pdm-crypt-module/src/lib/poly1305-donna-master/poly1305-donna.c

./src/app/pdm-crypt-module/src/lib/ecc/ecdh_curve25519.c

./src/app/pdm-crypt-module/src/lib/ecc/curve25519.c

./src/app/pdm-crypt-module/src/lib/ecc/fe25519.c
./src/app/pdm-crypt-module/src/lib/ecc/bigint.c
./src/app/pdm-crypt-module/src/lib/scrypt/src/hmac.c
./src/app/pdm-crypt-module/src/lib/scrypt/src/pbkdf2.c
./src/app/pdm-crypt-module/src/lib/scrypt/src/salsa20.c
./src/app/pdm-crypt-module/src/lib/scrypt/src/scrypt.c
./src/app/pdm-crypt-module/src/empp.cpp
./src/app/pdm-crypt-module/src/lib/scrypt/src/sha256.c
./src/app/pdm-crypt-module/src/lib/ec.cpp
./src/app/pdm-crypt-module/src/cc20core/cc20_multi.cpp

-fpermissive
-std=c++17
-O3
-DLINUX
-DWEB_RELEASE
-DSINGLETHREADING
-DWEB_RELEASE_LINUX_TEST

