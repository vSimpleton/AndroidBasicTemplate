## Android基础框架

### 使用了MVVM + Kotlin + Jetpack

### 一键初始化脚本
在命令行运行以下命令，支持传入两个参数，
* `--project` projectName
* `--package` packageName

```bash
sh -c "$(curl -fsSL https://raw.githubusercontent.com/vSimpleton/AndroidBasicTemplate/master/tools/install.sh)" "" --project HelloKitty --package com.android.hellokitty
```

缺省参数执行脚本时，会提示输入相应缺少的参数。