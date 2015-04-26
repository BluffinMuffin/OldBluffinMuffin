using System.Windows.Forms;

namespace BluffinMuffin.Client.Menu
{
    partial class MenuForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MenuForm));
            this.lblServerAddress = new System.Windows.Forms.Label();
            this.btnQuickMode = new Button();
            this.btnRegisteredModeConnect = new Button();
            this.btnRegisteredModeRegister = new Button();
            this.label1 = new System.Windows.Forms.Label();
            this.nudServerPort = new System.Windows.Forms.NumericUpDown();
            this.clstServerName = new System.Windows.Forms.ComboBox();
            ((System.ComponentModel.ISupportInitialize)(this.nudServerPort)).BeginInit();
            this.SuspendLayout();
            // 
            // lblServerAddress
            // 
            this.lblServerAddress.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblServerAddress.Location = new System.Drawing.Point(12, 9);
            this.lblServerAddress.Name = "lblServerAddress";
            this.lblServerAddress.Size = new System.Drawing.Size(353, 19);
            this.lblServerAddress.TabIndex = 14;
            this.lblServerAddress.Text = "Address:";
            this.lblServerAddress.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // btnQuickMode
            // 
            this.btnQuickMode.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnQuickMode.Image = global::BluffinMuffin.Client.Properties.Resources.learn;
            this.btnQuickMode.Location = new System.Drawing.Point(14, 126);
            this.btnQuickMode.Name = "btnQuickMode";
            this.btnQuickMode.Size = new System.Drawing.Size(353, 65);
            this.btnQuickMode.TabIndex = 0;
            this.btnQuickMode.Text = "Start QuickMode";
            this.btnQuickMode.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnQuickMode.UseVisualStyleBackColor = false;
            this.btnQuickMode.Click += new System.EventHandler(this.btnQuickMode_Click);
            // 
            // btnRegisteredModeConnect
            // 
            this.btnRegisteredModeConnect.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnRegisteredModeConnect.Image = global::BluffinMuffin.Client.Properties.Resources.cards;
            this.btnRegisteredModeConnect.Location = new System.Drawing.Point(14, 197);
            this.btnRegisteredModeConnect.Name = "btnRegisteredModeConnect";
            this.btnRegisteredModeConnect.Size = new System.Drawing.Size(353, 65);
            this.btnRegisteredModeConnect.TabIndex = 1;
            this.btnRegisteredModeConnect.Text = "Continue RegisteredMode";
            this.btnRegisteredModeConnect.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnRegisteredModeConnect.UseVisualStyleBackColor = false;
            this.btnRegisteredModeConnect.Click += new System.EventHandler(this.btnRegisteredModeConnect_Click);
            // 
            // btnRegisteredModeRegister
            // 
            this.btnRegisteredModeRegister.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnRegisteredModeRegister.Image = global::BluffinMuffin.Client.Properties.Resources.add_user;
            this.btnRegisteredModeRegister.Location = new System.Drawing.Point(14, 268);
            this.btnRegisteredModeRegister.Name = "btnRegisteredModeRegister";
            this.btnRegisteredModeRegister.Size = new System.Drawing.Size(353, 65);
            this.btnRegisteredModeRegister.TabIndex = 2;
            this.btnRegisteredModeRegister.Text = "Create RegisteredMode";
            this.btnRegisteredModeRegister.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnRegisteredModeRegister.UseVisualStyleBackColor = false;
            this.btnRegisteredModeRegister.Click += new System.EventHandler(this.btnRegisteredModeRegister_Click);
            // 
            // label1
            // 
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(12, 67);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(353, 19);
            this.label1.TabIndex = 16;
            this.label1.Text = "Port:";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // nudServerPort
            // 
            this.nudServerPort.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.nudServerPort.Location = new System.Drawing.Point(145, 89);
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
            this.nudServerPort.Size = new System.Drawing.Size(84, 23);
            this.nudServerPort.TabIndex = 17;
            this.nudServerPort.Value = new decimal(new int[] {
            4242,
            0,
            0,
            0});
            // 
            // clstServerName
            // 
            this.clstServerName.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.clstServerName.FormattingEnabled = true;
            this.clstServerName.Items.AddRange(new object[] {
            "127.0.0.1",
            "zeus42.is-a-geek.com"});
            this.clstServerName.Location = new System.Drawing.Point(12, 31);
            this.clstServerName.Name = "clstServerName";
            this.clstServerName.Size = new System.Drawing.Size(353, 24);
            this.clstServerName.TabIndex = 15;
            this.clstServerName.Text = "127.0.0.1";
            // 
            // MenuForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(381, 345);
            this.Controls.Add(this.nudServerPort);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.clstServerName);
            this.Controls.Add(this.lblServerAddress);
            this.Controls.Add(this.btnRegisteredModeRegister);
            this.Controls.Add(this.btnRegisteredModeConnect);
            this.Controls.Add(this.btnQuickMode);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "MenuForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Bluffin Muffin Client";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.MenuForm_FormClosed);
            ((System.ComponentModel.ISupportInitialize)(this.nudServerPort)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Button btnQuickMode;
        private System.Windows.Forms.Label lblServerAddress;
        private Button btnRegisteredModeConnect;
        private Button btnRegisteredModeRegister;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.NumericUpDown nudServerPort;
        private System.Windows.Forms.ComboBox clstServerName;
    }
}