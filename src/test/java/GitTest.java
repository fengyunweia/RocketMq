public class GitTest {
    /**
     * 合并错误了，或者提交代码想要撤回
     * 1：git revert 会把合并错误的版本信息认为我不想合并，撤回之后在合并就没有之前撤回部分的代码
     *
     * 2：git reset -- hard commintId 退回到之前指定的提交Id位置 然后使用git push -f 强制提交到远程即可
     */
}
