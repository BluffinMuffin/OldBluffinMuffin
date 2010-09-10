namespace BluffinPokerClient
{
    partial class MainForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.mainVSplit = new System.Windows.Forms.SplitContainer();
            this.nudServerPort = new System.Windows.Forms.NumericUpDown();
            this.lblServerPort = new System.Windows.Forms.Label();
            this.clstServerName = new System.Windows.Forms.ComboBox();
            this.lblServerAddress = new System.Windows.Forms.Label();
            this.mainHSplit = new System.Windows.Forms.SplitContainer();
            this.btnRegister = new System.Windows.Forms.Button();
            this.txtEmail1 = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.txtEmail2 = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.txtDisplayName = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.txtPassword2 = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.txtPassword1 = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.txtUser = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.lblTitNewPlayer = new System.Windows.Forms.Label();
            this.rightVSplit = new System.Windows.Forms.SplitContainer();
            this.chkRemember = new System.Windows.Forms.CheckBox();
            this.btnConnect = new System.Windows.Forms.Button();
            this.txtPassword = new System.Windows.Forms.TextBox();
            this.label8 = new System.Windows.Forms.Label();
            this.txtUsername = new System.Windows.Forms.TextBox();
            this.label9 = new System.Windows.Forms.Label();
            this.lblTitExistPlayer = new System.Windows.Forms.Label();
            this.txtPlayerName = new System.Windows.Forms.TextBox();
            this.lblPlayerName = new System.Windows.Forms.Label();
            this.btnStartTraining = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.mainVSplit.Panel1.SuspendLayout();
            this.mainVSplit.Panel2.SuspendLayout();
            this.mainVSplit.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudServerPort)).BeginInit();
            this.mainHSplit.Panel1.SuspendLayout();
            this.mainHSplit.Panel2.SuspendLayout();
            this.mainHSplit.SuspendLayout();
            this.rightVSplit.Panel1.SuspendLayout();
            this.rightVSplit.Panel2.SuspendLayout();
            this.rightVSplit.SuspendLayout();
            this.SuspendLayout();
            // 
            // mainVSplit
            // 
            this.mainVSplit.Dock = System.Windows.Forms.DockStyle.Fill;
            this.mainVSplit.FixedPanel = System.Windows.Forms.FixedPanel.Panel1;
            this.mainVSplit.IsSplitterFixed = true;
            this.mainVSplit.Location = new System.Drawing.Point(0, 0);
            this.mainVSplit.Name = "mainVSplit";
            this.mainVSplit.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // mainVSplit.Panel1
            // 
            this.mainVSplit.Panel1.BackColor = System.Drawing.SystemColors.Control;
            this.mainVSplit.Panel1.Controls.Add(this.nudServerPort);
            this.mainVSplit.Panel1.Controls.Add(this.lblServerPort);
            this.mainVSplit.Panel1.Controls.Add(this.clstServerName);
            this.mainVSplit.Panel1.Controls.Add(this.lblServerAddress);
            // 
            // mainVSplit.Panel2
            // 
            this.mainVSplit.Panel2.Controls.Add(this.mainHSplit);
            this.mainVSplit.Size = new System.Drawing.Size(624, 446);
            this.mainVSplit.SplitterDistance = 33;
            this.mainVSplit.SplitterWidth = 1;
            this.mainVSplit.TabIndex = 0;
            // 
            // nudServerPort
            // 
            this.nudServerPort.Location = new System.Drawing.Point(528, 7);
            this.nudServerPort.Maximum = new decimal(new int[] {
            65535,
            0,
            0,
            0});
            this.nudServerPort.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.nudServerPort.Name = "nudServerPort";
            this.nudServerPort.Size = new System.Drawing.Size(84, 20);
            this.nudServerPort.TabIndex = 15;
            this.nudServerPort.Value = new decimal(new int[] {
            4242,
            0,
            0,
            0});
            // 
            // lblServerPort
            // 
            this.lblServerPort.AutoSize = true;
            this.lblServerPort.Location = new System.Drawing.Point(459, 9);
            this.lblServerPort.Name = "lblServerPort";
            this.lblServerPort.Size = new System.Drawing.Size(63, 13);
            this.lblServerPort.TabIndex = 14;
            this.lblServerPort.Text = "Server Port:";
            // 
            // clstServerName
            // 
            this.clstServerName.FormattingEnabled = true;
            this.clstServerName.Items.AddRange(new object[] {
            "127.0.0.1",
            "SRV-PRJ-05.dmi.usherb.ca"});
            this.clstServerName.Location = new System.Drawing.Point(100, 6);
            this.clstServerName.Name = "clstServerName";
            this.clstServerName.Size = new System.Drawing.Size(353, 21);
            this.clstServerName.TabIndex = 13;
            this.clstServerName.Text = "127.0.0.1";
            // 
            // lblServerAddress
            // 
            this.lblServerAddress.AutoSize = true;
            this.lblServerAddress.Location = new System.Drawing.Point(12, 9);
            this.lblServerAddress.Name = "lblServerAddress";
            this.lblServerAddress.Size = new System.Drawing.Size(82, 13);
            this.lblServerAddress.TabIndex = 5;
            this.lblServerAddress.Text = "Server Address:";
            // 
            // mainHSplit
            // 
            this.mainHSplit.Dock = System.Windows.Forms.DockStyle.Fill;
            this.mainHSplit.FixedPanel = System.Windows.Forms.FixedPanel.Panel2;
            this.mainHSplit.IsSplitterFixed = true;
            this.mainHSplit.Location = new System.Drawing.Point(0, 0);
            this.mainHSplit.Name = "mainHSplit";
            // 
            // mainHSplit.Panel1
            // 
            this.mainHSplit.Panel1.BackColor = System.Drawing.SystemColors.Control;
            this.mainHSplit.Panel1.Controls.Add(this.btnRegister);
            this.mainHSplit.Panel1.Controls.Add(this.txtEmail1);
            this.mainHSplit.Panel1.Controls.Add(this.label7);
            this.mainHSplit.Panel1.Controls.Add(this.txtEmail2);
            this.mainHSplit.Panel1.Controls.Add(this.label6);
            this.mainHSplit.Panel1.Controls.Add(this.txtDisplayName);
            this.mainHSplit.Panel1.Controls.Add(this.label5);
            this.mainHSplit.Panel1.Controls.Add(this.txtPassword2);
            this.mainHSplit.Panel1.Controls.Add(this.label4);
            this.mainHSplit.Panel1.Controls.Add(this.txtPassword1);
            this.mainHSplit.Panel1.Controls.Add(this.label3);
            this.mainHSplit.Panel1.Controls.Add(this.txtUser);
            this.mainHSplit.Panel1.Controls.Add(this.label2);
            this.mainHSplit.Panel1.Controls.Add(this.lblTitNewPlayer);
            // 
            // mainHSplit.Panel2
            // 
            this.mainHSplit.Panel2.Controls.Add(this.rightVSplit);
            this.mainHSplit.Size = new System.Drawing.Size(624, 412);
            this.mainHSplit.SplitterDistance = 369;
            this.mainHSplit.SplitterWidth = 1;
            this.mainHSplit.TabIndex = 1;
            // 
            // btnRegister
            // 
            this.btnRegister.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnRegister.Location = new System.Drawing.Point(15, 370);
            this.btnRegister.Margin = new System.Windows.Forms.Padding(0);
            this.btnRegister.Name = "btnRegister";
            this.btnRegister.Size = new System.Drawing.Size(332, 33);
            this.btnRegister.TabIndex = 17;
            this.btnRegister.Text = "Create Player and Start Playing";
            this.btnRegister.UseVisualStyleBackColor = true;
            this.btnRegister.Click += new System.EventHandler(this.btnRegister_Click);
            // 
            // txtEmail1
            // 
            this.txtEmail1.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtEmail1.Location = new System.Drawing.Point(14, 221);
            this.txtEmail1.Name = "txtEmail1";
            this.txtEmail1.Size = new System.Drawing.Size(332, 22);
            this.txtEmail1.TabIndex = 16;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.Location = new System.Drawing.Point(12, 202);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(103, 16);
            this.label7.TabIndex = 15;
            this.label7.Text = "E-mail Address:";
            // 
            // txtEmail2
            // 
            this.txtEmail2.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtEmail2.Location = new System.Drawing.Point(15, 274);
            this.txtEmail2.Name = "txtEmail2";
            this.txtEmail2.Size = new System.Drawing.Size(332, 22);
            this.txtEmail2.TabIndex = 14;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(12, 255);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(151, 16);
            this.label6.TabIndex = 13;
            this.label6.Text = "Confirm E-mail Address:";
            // 
            // txtDisplayName
            // 
            this.txtDisplayName.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtDisplayName.Location = new System.Drawing.Point(13, 331);
            this.txtDisplayName.Name = "txtDisplayName";
            this.txtDisplayName.Size = new System.Drawing.Size(332, 22);
            this.txtDisplayName.TabIndex = 12;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(12, 312);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(148, 16);
            this.label5.TabIndex = 11;
            this.label5.Text = "Desired Display Name:";
            // 
            // txtPassword2
            // 
            this.txtPassword2.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtPassword2.Location = new System.Drawing.Point(14, 166);
            this.txtPassword2.Name = "txtPassword2";
            this.txtPassword2.PasswordChar = '*';
            this.txtPassword2.Size = new System.Drawing.Size(332, 22);
            this.txtPassword2.TabIndex = 10;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(12, 147);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(119, 16);
            this.label4.TabIndex = 9;
            this.label4.Text = "Confirm Password:";
            // 
            // txtPassword1
            // 
            this.txtPassword1.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtPassword1.Location = new System.Drawing.Point(13, 113);
            this.txtPassword1.Name = "txtPassword1";
            this.txtPassword1.PasswordChar = '*';
            this.txtPassword1.Size = new System.Drawing.Size(332, 22);
            this.txtPassword1.TabIndex = 8;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(11, 94);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(122, 16);
            this.label3.TabIndex = 7;
            this.label3.Text = "Desired Password:";
            // 
            // txtUser
            // 
            this.txtUser.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtUser.Location = new System.Drawing.Point(14, 57);
            this.txtUser.Name = "txtUser";
            this.txtUser.Size = new System.Drawing.Size(332, 22);
            this.txtUser.TabIndex = 6;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(12, 38);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(122, 16);
            this.label2.TabIndex = 5;
            this.label2.Text = "Desired username:";
            // 
            // lblTitNewPlayer
            // 
            this.lblTitNewPlayer.Dock = System.Windows.Forms.DockStyle.Top;
            this.lblTitNewPlayer.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblTitNewPlayer.Location = new System.Drawing.Point(0, 0);
            this.lblTitNewPlayer.Name = "lblTitNewPlayer";
            this.lblTitNewPlayer.Size = new System.Drawing.Size(369, 38);
            this.lblTitNewPlayer.TabIndex = 0;
            this.lblTitNewPlayer.Text = "New Player";
            this.lblTitNewPlayer.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // rightVSplit
            // 
            this.rightVSplit.Dock = System.Windows.Forms.DockStyle.Fill;
            this.rightVSplit.FixedPanel = System.Windows.Forms.FixedPanel.Panel2;
            this.rightVSplit.IsSplitterFixed = true;
            this.rightVSplit.Location = new System.Drawing.Point(0, 0);
            this.rightVSplit.Name = "rightVSplit";
            this.rightVSplit.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // rightVSplit.Panel1
            // 
            this.rightVSplit.Panel1.BackColor = System.Drawing.SystemColors.Control;
            this.rightVSplit.Panel1.Controls.Add(this.chkRemember);
            this.rightVSplit.Panel1.Controls.Add(this.btnConnect);
            this.rightVSplit.Panel1.Controls.Add(this.txtPassword);
            this.rightVSplit.Panel1.Controls.Add(this.label8);
            this.rightVSplit.Panel1.Controls.Add(this.txtUsername);
            this.rightVSplit.Panel1.Controls.Add(this.label9);
            this.rightVSplit.Panel1.Controls.Add(this.lblTitExistPlayer);
            // 
            // rightVSplit.Panel2
            // 
            this.rightVSplit.Panel2.BackColor = System.Drawing.SystemColors.Control;
            this.rightVSplit.Panel2.Controls.Add(this.txtPlayerName);
            this.rightVSplit.Panel2.Controls.Add(this.lblPlayerName);
            this.rightVSplit.Panel2.Controls.Add(this.btnStartTraining);
            this.rightVSplit.Panel2.Controls.Add(this.label1);
            this.rightVSplit.Size = new System.Drawing.Size(254, 412);
            this.rightVSplit.SplitterDistance = 266;
            this.rightVSplit.SplitterWidth = 1;
            this.rightVSplit.TabIndex = 0;
            // 
            // chkRemember
            // 
            this.chkRemember.AutoSize = true;
            this.chkRemember.Enabled = false;
            this.chkRemember.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F);
            this.chkRemember.Location = new System.Drawing.Point(32, 165);
            this.chkRemember.Name = "chkRemember";
            this.chkRemember.Size = new System.Drawing.Size(117, 20);
            this.chkRemember.TabIndex = 14;
            this.chkRemember.Text = "Remember Me";
            this.chkRemember.UseVisualStyleBackColor = true;
            // 
            // btnConnect
            // 
            this.btnConnect.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnConnect.Location = new System.Drawing.Point(32, 210);
            this.btnConnect.Margin = new System.Windows.Forms.Padding(0);
            this.btnConnect.Name = "btnConnect";
            this.btnConnect.Size = new System.Drawing.Size(200, 33);
            this.btnConnect.TabIndex = 13;
            this.btnConnect.Text = "Start Playing";
            this.btnConnect.UseVisualStyleBackColor = true;
            this.btnConnect.Click += new System.EventHandler(this.btnConnect_Click);
            // 
            // txtPassword
            // 
            this.txtPassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtPassword.Location = new System.Drawing.Point(32, 125);
            this.txtPassword.Name = "txtPassword";
            this.txtPassword.PasswordChar = '*';
            this.txtPassword.Size = new System.Drawing.Size(200, 22);
            this.txtPassword.TabIndex = 12;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label8.Location = new System.Drawing.Point(29, 106);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(71, 16);
            this.label8.TabIndex = 11;
            this.label8.Text = "Password:";
            // 
            // txtUsername
            // 
            this.txtUsername.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtUsername.Location = new System.Drawing.Point(32, 69);
            this.txtUsername.Name = "txtUsername";
            this.txtUsername.Size = new System.Drawing.Size(201, 22);
            this.txtUsername.TabIndex = 10;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label9.Location = new System.Drawing.Point(29, 50);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(71, 16);
            this.label9.TabIndex = 9;
            this.label9.Text = "Username";
            // 
            // lblTitExistPlayer
            // 
            this.lblTitExistPlayer.Dock = System.Windows.Forms.DockStyle.Top;
            this.lblTitExistPlayer.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblTitExistPlayer.Location = new System.Drawing.Point(0, 0);
            this.lblTitExistPlayer.Name = "lblTitExistPlayer";
            this.lblTitExistPlayer.Size = new System.Drawing.Size(254, 38);
            this.lblTitExistPlayer.TabIndex = 1;
            this.lblTitExistPlayer.Text = "Existing Player";
            this.lblTitExistPlayer.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // txtPlayerName
            // 
            this.txtPlayerName.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtPlayerName.Location = new System.Drawing.Point(32, 70);
            this.txtPlayerName.Name = "txtPlayerName";
            this.txtPlayerName.Size = new System.Drawing.Size(201, 22);
            this.txtPlayerName.TabIndex = 4;
            this.txtPlayerName.Text = "Player";
            // 
            // lblPlayerName
            // 
            this.lblPlayerName.AutoSize = true;
            this.lblPlayerName.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPlayerName.Location = new System.Drawing.Point(29, 51);
            this.lblPlayerName.Name = "lblPlayerName";
            this.lblPlayerName.Size = new System.Drawing.Size(90, 16);
            this.lblPlayerName.TabIndex = 3;
            this.lblPlayerName.Text = "Player Name:";
            // 
            // btnStartTraining
            // 
            this.btnStartTraining.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnStartTraining.Location = new System.Drawing.Point(32, 109);
            this.btnStartTraining.Margin = new System.Windows.Forms.Padding(0);
            this.btnStartTraining.Name = "btnStartTraining";
            this.btnStartTraining.Size = new System.Drawing.Size(200, 33);
            this.btnStartTraining.TabIndex = 3;
            this.btnStartTraining.Text = "Start Training";
            this.btnStartTraining.UseVisualStyleBackColor = true;
            this.btnStartTraining.Click += new System.EventHandler(this.btnStartTraining_Click);
            // 
            // label1
            // 
            this.label1.Dock = System.Windows.Forms.DockStyle.Top;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(0, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(254, 38);
            this.label1.TabIndex = 2;
            this.label1.Text = "Training";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlDark;
            this.ClientSize = new System.Drawing.Size(624, 446);
            this.Controls.Add(this.mainVSplit);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.SizableToolWindow;
            this.MinimumSize = new System.Drawing.Size(640, 480);
            this.Name = "MainForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Bluffin Muffin Client";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.MainForm_FormClosed);
            this.mainVSplit.Panel1.ResumeLayout(false);
            this.mainVSplit.Panel1.PerformLayout();
            this.mainVSplit.Panel2.ResumeLayout(false);
            this.mainVSplit.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.nudServerPort)).EndInit();
            this.mainHSplit.Panel1.ResumeLayout(false);
            this.mainHSplit.Panel1.PerformLayout();
            this.mainHSplit.Panel2.ResumeLayout(false);
            this.mainHSplit.ResumeLayout(false);
            this.rightVSplit.Panel1.ResumeLayout(false);
            this.rightVSplit.Panel1.PerformLayout();
            this.rightVSplit.Panel2.ResumeLayout(false);
            this.rightVSplit.Panel2.PerformLayout();
            this.rightVSplit.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.SplitContainer mainVSplit;
        private System.Windows.Forms.SplitContainer mainHSplit;
        private System.Windows.Forms.Label lblTitNewPlayer;
        private System.Windows.Forms.SplitContainer rightVSplit;
        private System.Windows.Forms.Label lblTitExistPlayer;
        private System.Windows.Forms.Button btnStartTraining;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label lblPlayerName;
        private System.Windows.Forms.TextBox txtPlayerName;
        private System.Windows.Forms.Label lblServerAddress;
        private System.Windows.Forms.ComboBox clstServerName;
        private System.Windows.Forms.Label lblServerPort;
        private System.Windows.Forms.NumericUpDown nudServerPort;
        private System.Windows.Forms.Button btnRegister;
        private System.Windows.Forms.TextBox txtEmail1;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TextBox txtEmail2;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox txtDisplayName;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox txtPassword2;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox txtPassword1;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox txtUser;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.CheckBox chkRemember;
        private System.Windows.Forms.Button btnConnect;
        private System.Windows.Forms.TextBox txtPassword;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox txtUsername;
        private System.Windows.Forms.Label label9;

    }
}