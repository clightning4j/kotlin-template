# kotlin-template
A minimal template for projects based on [JRPClightning](https://github.com/vincenzopalazzo/JRPClightning). This should get you started if you want to develop a core lightning plugin using Kotlin.

## Install
Java produces a jar and core lightning needs a bash script to run it!
The gradle is able to compile the plugin in a jar, and generate a runnable script that core lightning use to run it
```bash
./gradlew createRunnableScript
```

After the gradle process, you will have the jar inside the `build/libs/app-all.jar` and the script `app-gen.sh`
in the `app` directory of the project.

Now you can put the gradle script inside the c-lightning plugin directory or use the `--plugin="PATH/TO/THE/app-gen.sh`.


## Code Style
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

> We live in a world where robots can drive a car, so we shouldn't just write code, we should write elegant code.

This repository use [ktlint](https://github.com/pinterest/ktlint) to maintains the code of the repository elegant, so
before submit the code check the Kotlin format with the following command on the root of the directory

```bash
./gradlew formatKotlin
```


## Built with

- [JRPClightning](https://github.com/vincenzopalazzo/JRPClightning)

## Support
If you like the library and want to support it, please considerer to donate with the following system

- [liberapay.com/vincenzopalazzo](https://liberapay.com/vincenzopalazzo)
- [3BQ8qbn8hLdmBKEjt1Hj1Z6SiDsnjJurfU](bitcoin:3BQ8qbn8hLdmBKEjt1Hj1Z6SiDsnjJurfU)
- [Github support](https://github.com/sponsors/vincenzopalazzo)

## License

<div align="center">
  <img src="https://opensource.org/files/osi_keyhole_300X300_90ppi_0.png" width="150" height="150"/>
</div>

It is a core lightning plugin to override Bitcoin backend plugin with esplora.

Copyright (C) 2020-2022 Vincenzo Palazzo vincenzopalazzodev@gmail.com
``

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
